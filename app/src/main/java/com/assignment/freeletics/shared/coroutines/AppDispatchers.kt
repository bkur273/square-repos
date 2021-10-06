@file:Suppress("unused", "unused")

package com.assignment.freeletics.shared.coroutines

import kotlinx.coroutines.CoroutineDispatcher

class AppDispatchers(
    val main: CoroutineDispatcher,
    val io: CoroutineDispatcher,
    val background: CoroutineDispatcher
)
