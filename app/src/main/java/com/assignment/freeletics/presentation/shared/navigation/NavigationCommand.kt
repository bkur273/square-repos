package com.assignment.freeletics.presentation.shared.navigation

import androidx.navigation.NavController
import androidx.navigation.NavDirections

@Suppress("unused")
interface NavigationCommand {

    fun navigate(navController: NavController)

    class To(private val navDirections: NavDirections) : NavigationCommand {
        override fun navigate(navController: NavController) {
            navController.navigate(navDirections)
        }
    }

    object Back : NavigationCommand {
        override fun navigate(navController: NavController) {
            navController.popBackStack()
        }
    }

}