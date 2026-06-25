package com.navigation.api

import kotlinx.coroutines.flow.SharedFlow

interface Navigator {
    val navigationCommands: SharedFlow<NavigationCommand>

    fun navigateTo(route: Any)
    fun navigateUp()
}

sealed interface NavigationCommand {
    data class To(val route: Any) : NavigationCommand
    data object Back : NavigationCommand
}