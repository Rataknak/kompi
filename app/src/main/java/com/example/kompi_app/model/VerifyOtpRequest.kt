package com.example.kompi_app.model

data class VerifyOtpRequest(
    val tempToken: String,
    val otp: String
)