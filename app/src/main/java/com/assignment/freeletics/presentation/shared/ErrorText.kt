package com.assignment.freeletics.presentation.shared

class ErrorDialogData(
    val throwable: Throwable,
    val onDismiss: () -> Unit = {}
)