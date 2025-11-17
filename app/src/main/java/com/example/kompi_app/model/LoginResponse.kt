package com.example.kompi_app.model

// Remove the old LoginResponse and User classes, replace with:
data class LoginResponse(
    val accessToken: String,
    val id: Long,
    val username: String,
    val email: String,
    val role: String
)