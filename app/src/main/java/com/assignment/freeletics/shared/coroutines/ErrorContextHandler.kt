package com.assignment.freeletics.shared.coroutines

import android.util.Log
import com.assignment.freeletics.data.network.shared.NetworkErrorType
import com.assignment.freeletics.data.network.shared.NetworkException
import com.assignment.freeletics.presentation.shared.ErrorDialogData
import com.assignment.freeletics.presentation.shared.state.MutableViewStateObserver
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlin.coroutines.CoroutineContext

interface ErrorContextHandler {

    fun handle(block: ErrorContextBuilder.(Throwable) -> Unit): CoroutineContext
    fun handle(exception: Throwable, block: ErrorContextBuilder.(Throwable) -> Unit)

    class Impl(
        private val mutableViewStateObserver: MutableViewStateObserver
    ) : ErrorContextHandler {


        override fun handle(block: ErrorContextBuilder.(Throwable) -> Unit): CoroutineContext {
            val errorContextHandler = ErrorContextBuilder()

            return CoroutineExceptionHandler { _, throwable ->
                handle(errorContextHandler, block, throwable)
            }
        }

        override fun handle(
            exception: Throwable,
            block: ErrorContextBuilder.(Throwable) -> Unit
        ) {
            handle(ErrorContextBuilder(), block, exception)
        }

        private fun handle(
            errorContextHandler: ErrorContextBuilder,
            block: ErrorContextBuilder.(Throwable) -> Unit,
            throwable: Throwable
        ) {
            errorContextHandler.block(throwable)
            val result = errorContextHandler.build()
            if (result.showDialog) {
                showErrorDialog(result, throwable)
            }
            log(result.loggingStrategies, throwable)
            mutableViewStateObserver.setLoadingState(false)
        }

        private fun showErrorDialog(result: ErrorContextBuilder.Result, exception: Throwable) {
            when {
                result.dialogText.isNotEmpty() -> mutableViewStateObserver.setError(
                    ErrorDialogData(
                        IllegalStateException(result.dialogText, exception),
                        result.onDismissDialog
                    )
                )
                exception is NetworkException -> {
                    val userFriendlyException = when (exception.error) {
                        NetworkErrorType.NoInternet -> IllegalStateException(
                            "Check your internet connection",
                            exception
                        )
                        else -> exception
                    }
                    mutableViewStateObserver.setError(
                        ErrorDialogData(userFriendlyException, result.onDismissDialog)
                    )
                }
                else -> mutableViewStateObserver.setError(
                    ErrorDialogData(
                        IllegalStateException("Unknown Error Occurred", exception),
                        result.onDismissDialog
                    )
                )
            }
        }

        private fun log(
            strategies: Array<out LoggingStrategy>,
            exc: Throwable
        ) {
            strategies.forEach { strategy ->
                when (strategy) {
                    LoggingStrategy.LoggingAnalytics -> {
                        //TODO log error on firebase
                    }
                    is LoggingStrategy.Logcat -> {
                        when (strategy.level) {
                            Log.ERROR -> Log.e("Network Error", strategy.message, exc)
                            Log.DEBUG -> Log.d("Network Debug", exc.message ?: "")
                            else -> Log.i("Network Info", exc.message ?: "")
                        }
                    }
                }
            }
        }

    }

}

class ErrorContextBuilder {

    var loggingStrategies: Array<LoggingStrategy> = emptyArray()
    var showDialog: Boolean = false
    var dialogText: String = ""
    var onDialogDismiss: () -> Unit = {}

    fun build() = Result(
        loggingStrategies,
        showDialog,
        dialogText,
        onDialogDismiss
    )

    class Result(
        val loggingStrategies: Array<LoggingStrategy>,
        val showDialog: Boolean,
        val dialogText: String,
        val onDismissDialog: () -> Unit
    )
}