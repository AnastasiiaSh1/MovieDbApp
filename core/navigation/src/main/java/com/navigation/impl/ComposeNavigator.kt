package com.navigation.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.navigation.api.Navigator
import com.navigation.api.Home
import com.navigation.api.Details

internal class ComposeNavigator(private val nav: NavHostController) : Navigator {
    override fun navigateToHome() { nav.navigate(Home) }
    override fun navigateToDetails() { nav.navigate(Details) }
    override fun navigateBack() { nav.popBackStack() }
}

@Composable
fun NavigationHost(
    startDestination: Any,
    builder: NavGraphBuilder.(Navigator) -> Unit
) {
    val navController = rememberNavController()
    val navigator = remember { ComposeNavigator(navController) }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        builder(navigator)
    }
}