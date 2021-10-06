package com.assignment.freeletics.presentation.shared.state

import com.assignment.freeletics.presentation.shared.ErrorDialogData
import com.assignment.freeletics.presentation.shared.navigation.NavigationCommand
import kotlinx.coroutines.flow.Flow

interface ViewStateObserver {

    fun errorText(): Flow<ErrorDialogData>
    fun navigationState(): Flow<NavigationCommand>
    fun showLoading(): Flow<Boolean>

}