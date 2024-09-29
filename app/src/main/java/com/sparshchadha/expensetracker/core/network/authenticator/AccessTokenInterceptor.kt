package com.sparshchadha.expensetracker.core.network.authenticator

import com.sparshchadha.expensetracker.core.network.api.ExpenseTrackerAPI
import com.sparshchadha.expensetracker.core.storage.shared_preference.ExpenseTrackerSharedPref
import com.sparshchadha.expensetracker.feature.auth.data.remote.dto.AccessTokenRequest
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AccessTokenInterceptor @Inject constructor(
    private val sharedPref: ExpenseTrackerSharedPref
) : Interceptor {

    private val api: ExpenseTrackerAPI by lazy {
        Retrofit.Builder().baseUrl(ExpenseTrackerAPI.BASE_URL)
            .client(provideOKHttpClientWithoutAuthInterceptor())
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(ExpenseTrackerAPI::class.java)
    }

    override fun intercept(chain: Interceptor.Chain): Response = synchronized(this) {
        runBlocking {
            val accessToken = sharedPref.getAccessToken()
            val refreshToken = sharedPref.getRefreshToken()

            val request: Request =
                newRequestWithAccessToken(chain.request(), accessToken).newBuilder().build()

            if (accessToken.isBlank()) {
                val reqWithoutToken = newRequestWithoutAccessToken(request)
                return@runBlocking chain.proceed(reqWithoutToken)
            }

            val response = chain.proceed(request)

            if (response.code == 401) {
                try {
                    val refreshTokenResponse = api.newAccessToken(AccessTokenRequest(refreshToken))
                    val body = refreshTokenResponse.body()

                    //If refresh token also expires we Logout the user
                    if (refreshTokenResponse.code() == 401 || body == null || body.areTokensEmpty()) {
                        sharedPref.saveAccessToken("")
                        sharedPref.saveRefreshToken("")

                        val reqWithoutAuth = newRequestWithoutAccessToken(request)
                        return@runBlocking chain.proceed(reqWithoutAuth)
                    }

                    sharedPref.saveAccessToken(body.access)

                    val reqWithNewToken = newRequestWithAccessToken(request, body.access)
                    response.close()
                    return@runBlocking chain.proceed(reqWithNewToken)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            return@runBlocking response
        }
    }

    private fun newRequestWithAccessToken(request: Request, accessToken: String): Request {
        return request.newBuilder().header("Authorization", "Bearer $accessToken").build()
    }

    private fun newRequestWithoutAccessToken(request: Request): Request {
        return request.newBuilder().removeHeader("Authorization").build()
    }

    private fun provideOKHttpClientWithoutAuthInterceptor(): OkHttpClient =
        OkHttpClient.Builder().readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS).build()
}