package com.example.kompi_app.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    var currentScreen by remember { mutableStateOf("welcome") }

    when (currentScreen) {
        "welcome" -> WelcomeScreen(
            onCreateAccount = { currentScreen = "login" }
        )
        "login" -> LoginScreen(
            onLoginClick = { currentScreen = "main" }
        )
        "main" -> MainScreen(
            onLogout = { currentScreen = "welcome" }
        )
    }
}