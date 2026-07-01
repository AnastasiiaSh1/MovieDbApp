package com.moviedb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.composable
import com.moviedb.ui.theme.MovieDbAppTheme
import com.navigation.impl.NavigationHost
import com.navigation.api.Home
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import com.navigation.api.Details

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieDbAppTheme {
                MainAppNavigation()
            }
        }
    }
}

@Composable
fun MainAppNavigation() {
    NavigationHost(startDestination = Home::class) { navigator ->

        composable<Home> {
            Button(onClick = { navigator.navigateToDetails() }) {
                Text("Go to Details")
            }
        }

        composable<Details> {
            Button(onClick = { navigator.navigateBack() }) {
                Text("Go Back")
            }
        }
    }
}