package com.navigation.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.navigation.api.Navigator


internal class ComposeNavigator(private val nav: NavHostController) : Navigator {
    override fun navigateTo(destination: Any) {
        nav.navigate(destination)
    }
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