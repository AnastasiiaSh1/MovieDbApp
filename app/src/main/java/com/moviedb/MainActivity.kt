package com.moviedb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.moviedb.ui.theme.MovieDbAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieDbAppTheme {
                MainAppNavigation()
            }
        }
    }
}

@Composable
fun MainAppNavigation() {
    val navController = rememberNavController()

    val navigator = remember { com.navigation.impl.ComposeNavigator() }

    LaunchedEffect(navController) {
        navigator.navigationCommands.collect { command ->
            when (command) {
                is com.navigation.api.NavigationCommand.To -> navController.navigate(command.route)
                is com.navigation.api.NavigationCommand.Back -> navController.navigateUp()
            }
        }
    }

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            androidx.compose.material3.Button(onClick = { navigator.navigateTo("details") }) {
                androidx.compose.material3.Text("Go to Details")
            }
        }
        composable("details") {
            androidx.compose.material3.Button(onClick = { navigator.navigateUp() }) {
                androidx.compose.material3.Text("Go Back")
            }
        }
    }
}
