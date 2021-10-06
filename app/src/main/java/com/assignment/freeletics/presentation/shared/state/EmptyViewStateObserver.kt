package com.assignment.freeletics.presentation.shared.state

import com.assignment.freeletics.presentation.shared.ErrorDialogData
import com.assignment.freeletics.presentation.shared.navigation.NavigationCommand
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

object EmptyViewStateObserver : ViewStateObserver {
    override fun errorText(): Flow<ErrorDialogData> = emptyFlow()

    override fun navigationState(): Flow<NavigationCommand> = emptyFlow()

    override fun showLoading(): Flow<Boolean> = emptyFlow()
}