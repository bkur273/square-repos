package com.assignment.freeletics.shared.coroutines

sealed class LoggingStrategy {

    object LoggingAnalytics : LoggingStrategy()
    data class Logcat(val level: Int, val message: String = ""): LoggingStrategy()

}