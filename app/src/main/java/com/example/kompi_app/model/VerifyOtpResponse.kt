package com.example.kompi_app.model
data class VerifyOtpResponse(
    val success: Boolean,
    val message: String,
    val token: String  // Changed from verifiedEmailToken to token
)