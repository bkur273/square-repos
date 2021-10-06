package com.assignment.freeletics.data.network.shared

import android.util.Log

inline fun <reified T> NetworkResponse<T>.result(): T {
    return when (val networkResponse = this) {
        is NetworkResponse.Success -> networkResponse.body
        is NetworkResponse.ApiError -> throw NetworkException(
            networkResponse.type,
            networkResponse.body
        )
        is NetworkResponse.NetworkError -> throw NetworkException(
            NetworkErrorType.NoInternet,
            networkResponse.error.message ?: ""
        )
        is NetworkResponse.UnknownError -> {

            val networkException = NetworkException(
                NetworkErrorType.BadRequest,
                networkResponse.error.message ?: ""
            )

            Log.e(
                "While Mapping",
                "Unknown Error",
                networkException
            )

            throw networkException
        }
        NetworkResponse.SuccessNoBody -> {
            if (T::class == Unit::class) {
                Unit as T
            } else
                error("If SuccessNoBody is returned return type should always be Unit")
        }
    }
}