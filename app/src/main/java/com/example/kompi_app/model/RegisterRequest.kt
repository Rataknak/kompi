package com.example.kompi_app.model

data class RegisterRequest(
    val verifiedEmailToken: String,
    val username: String,
    val password: String,
    val email: String,
    val phoneNumber: String,
    val role: String = "USER"
)