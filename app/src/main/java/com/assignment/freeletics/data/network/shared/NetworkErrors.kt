package com.assignment.freeletics.data.network.shared

class NetworkException(val error: NetworkErrorType, errorMessage: String) : Throwable(errorMessage)

enum class NetworkErrorType {
    BadRequest,
    NoInternet;
}
