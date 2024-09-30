package com.sparshchadha.expensetracker.feature.auth.ui.client

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.sparshchadha.expensetracker.R

class GoogleSignInUIClient(
    val context: Context,
) {
    suspend fun getCredential(onSignInComplete: (GoogleSignInResult) -> Unit) {
        val credentialManager = CredentialManager.create(context)
        val result = credentialManager.getCredential(
            context = context,
            request = createCredentialRequest()
        )

        handleSignIn(result, onSignInComplete)
    }

    private fun createCredentialRequest(): GetCredentialRequest {
        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(ContextCompat.getString(context, R.string.default_web_client_id))
            .setAutoSelectEnabled(true)
            .build()

        return GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()
    }

    private fun handleSignIn(
        result: GetCredentialResponse,
        onSignInComplete: (GoogleSignInResult) -> Unit,
    ) {
        var signInResult: GoogleSignInResult

        when (val credential = result.credential) {
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        val googleIdTokenCredential = GoogleIdTokenCredential
                            .createFrom(credential.data)
                        signInResult = GoogleSignInResult(
                            idToken = googleIdTokenCredential.idToken,
                            isSuccessful = true
                        )


                    } catch (e: GoogleIdTokenParsingException) {
                        signInResult = GoogleSignInResult(
                            isSuccessful = false,
                            errorMessage = e.message ?: "Something went wrong!"
                        )
                    }
                } else {
                    signInResult = GoogleSignInResult(
                        errorMessage = "Unexpected type of credential, please try again.",
                        isSuccessful = false
                    )
                }
            }

            else -> {
                signInResult = GoogleSignInResult(
                    errorMessage = "Unexpected type of credential, please try again.",
                    isSuccessful = false
                )
            }
        }

        onSignInComplete(signInResult)
    }
}