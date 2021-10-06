package com.assignment.freeletics.data.network.shared

import java.io.IOException

sealed class NetworkResponse<out T> {
    /**
     * Success response with body
     */
    data class Success<T : Any>(val body: T) : NetworkResponse<T>()

    object SuccessNoBody : NetworkResponse<Nothing>()

    /**
     * Failure response with body
     */
    data class ApiError(val body: String, val code: Int, val type: NetworkErrorType) : NetworkResponse<Nothing>()

    /**
     * Network error
     */
    data class NetworkError(val error: IOException) : NetworkResponse<Nothing>()

    /**
     * For example, json parsing error
     */
    data class UnknownError(val error: Throwable) : NetworkResponse<Nothing>()
}