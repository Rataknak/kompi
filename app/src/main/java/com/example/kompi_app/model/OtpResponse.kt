package com.example.kompi_app.model

data class OtpResponse(
    val success: Boolean,
    val message: String,
    val token: String  // Should match what backend returns
)