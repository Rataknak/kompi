package com.example.kompi_app.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import com.example.kompi_app.screen.FavoritesScreen

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
            onLogout = { currentScreen = "welcome" },
            onNotificationClick = { currentScreen = "notification" },
            onPersonalClick = { currentScreen = "personal" },
            onFavoritesClick = { currentScreen = "favorites_profile" },
            onLanguageClick = { currentScreen = "language" }
        )
        "notification" -> NotificationScreen(
            onBackClick = { currentScreen = "main" }
        )
        "personal" -> PersonalScreen(
            onBackClick = { currentScreen = "main" }
        )
        "favorites_profile" -> FavoritesScreen(
            onBackClick = { currentScreen = "main" }
        )
        "language" -> LanguageScreen(
            onBackClick = { currentScreen = "main" }
        )
    }
}
