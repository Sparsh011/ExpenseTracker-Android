package com.sparshchadha.expensetracker.core.domain

sealed class Resource<T>(val data: T? = null, val error: Throwable? = null){
    class Success<T>(data: T? = null): Resource<T>(data)
    class Loading<T>(data: T? = null): Resource<T>(data)
    class Error<T>(data: T? = null, error: Throwable? = null): Resource<T>(data, error)
}