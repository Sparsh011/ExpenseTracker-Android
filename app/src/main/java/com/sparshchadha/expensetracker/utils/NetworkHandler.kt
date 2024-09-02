package com.sparshchadha.expensetracker.utils

import android.net.Network

sealed class NetworkHandler<T>(val data: T? = null, val error: Throwable? = null){
    class Success<T>(data: T? = null): NetworkHandler<T>(data)
    class Loading<T>(data: T? = null): NetworkHandler<T>(data)
    class Error<T>(data: T? = null, error: Throwable? = null): NetworkHandler<T>(data, error)
}