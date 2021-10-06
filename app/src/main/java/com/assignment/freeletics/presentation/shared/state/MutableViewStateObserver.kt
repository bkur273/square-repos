package com.assignment.freeletics.presentation.shared.state

import com.assignment.freeletics.presentation.shared.ErrorDialogData
import com.assignment.freeletics.presentation.shared.navigation.NavigationCommand
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

interface MutableViewStateObserver : ViewStateObserver {

    fun setError(exc: ErrorDialogData)
    fun setNavigationCommand(navigationCommand: NavigationCommand)
    fun setLoadingState(showLoading: Boolean)

    class Impl : MutableViewStateObserver {

        private val errorDialog = Channel<ErrorDialogData>(Channel.BUFFERED)
        private val navigationCommand = Channel<NavigationCommand>(Channel.BUFFERED)
        private val showLoading = Channel<Boolean>(Channel.BUFFERED)

        override fun setError(exc: ErrorDialogData) {
            errorDialog.trySendBlocking(exc)
        }

        override fun setNavigationCommand(navigationCommand: NavigationCommand) {
            this.navigationCommand.trySendBlocking(navigationCommand)
        }

        override fun setLoadingState(showLoading: Boolean) {
            this.showLoading.trySendBlocking(showLoading)
        }

        override fun errorText(): Flow<ErrorDialogData> = errorDialog.receiveAsFlow()

        override fun navigationState(): Flow<NavigationCommand> = navigationCommand.receiveAsFlow()

        override fun showLoading(): Flow<Boolean> = showLoading.receiveAsFlow()

    }

}