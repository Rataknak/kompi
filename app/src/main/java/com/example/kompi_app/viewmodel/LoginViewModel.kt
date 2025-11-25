package com.example.kompi_app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kompi_app.model.*
import com.example.kompi_app.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    private val _signupState = MutableStateFlow<SignupState>(SignupState.Idle)
    val signupState: StateFlow<SignupState> = _signupState.asStateFlow()

    // Login function (existing)
    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            try {
                println("🔵 Sending login request: $email / $password")
                val response = RetrofitInstance.apiService.login(LoginRequest(email, password))
                println("🔵 Response code: ${response.code()}")
                println("🔵 Response body: ${response.body()}")

                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    println("🔵 Login response: $loginResponse")

                    if (loginResponse != null) {
                        _loginState.value = LoginState.Success(loginResponse)
                    } else {
                        _loginState.value = LoginState.Error("Login failed - empty response")
                    }
                } else {
                    val errorCode = response.code()
                    val errorBody = response.errorBody()?.string() ?: "No error body"
                    println("🔴 HTTP Error $errorCode: $errorBody")

                    when (errorCode) {
                        401 -> _loginState.value = LoginState.Error("Invalid email or password")
                        400 -> _loginState.value = LoginState.Error("Bad request: $errorBody")
                        500 -> _loginState.value = LoginState.Error("Server error")
                        else -> _loginState.value = LoginState.Error("HTTP $errorCode: $errorBody")
                    }
                }
            } catch (e: Exception) {
                println("🔴 Exception: ${e.message}")
                _loginState.value = LoginState.Error("Network error: ${e.message}")
            }
        }
    }

    // Step 1: Generate OTP
    fun generateOtp(email: String) {
        viewModelScope.launch {
            _signupState.value = SignupState.GeneratingOtp
            try {
                println("🔵 Generating OTP for: $email")
                val response = RetrofitInstance.apiService.generateOtp(OtpRequest(email))
                println("🔵 OTP Response code: ${response.code()}")
                println("🔵 OTP Response body: ${response.body()}")

                if (response.isSuccessful) {
                    val otpResponse = response.body()
                    if (otpResponse != null) {
                        _signupState.value = SignupState.OtpGenerated(otpResponse.token)
                    } else {
                        _signupState.value = SignupState.Error("Failed to generate OTP")
                    }
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Unknown error"
                    _signupState.value = SignupState.Error("OTP generation failed: $errorBody")
                }
            } catch (e: Exception) {
                println("🔴 OTP Exception: ${e.message}")
                _signupState.value = SignupState.Error("Network error: ${e.message}")
            }
        }
    }

    // Step 2: Verify OTP
    fun verifyOtp(tempToken: String, otp: String) {
        viewModelScope.launch {
            _signupState.value = SignupState.VerifyingOtp
            try {
                println("🔵 Verifying OTP: $otp with token: $tempToken")
                val response = RetrofitInstance.apiService.verifyOtp(VerifyOtpRequest(tempToken, otp))
                println("🔵 Verify OTP Response code: ${response.code()}")
                println("🔵 Verify OTP Response body: ${response.body()}")

                if (response.isSuccessful) {
                    val verifyResponse = response.body()
                    if (verifyResponse != null && verifyResponse.success) {
                        // ✅ Changed from verifiedEmailToken to token
                        _signupState.value = SignupState.OtpVerified(verifyResponse.token)
                    } else {
                        _signupState.value = SignupState.Error("Failed to verify OTP: ${verifyResponse?.message}")
                    }
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Unknown error"
                    _signupState.value = SignupState.Error("OTP verification failed: $errorBody")
                }
            } catch (e: Exception) {
                println("🔴 Verify OTP Exception: ${e.message}")
                _signupState.value = SignupState.Error("Network error: ${e.message}")
            }
        }
    }

    // Step 3: Register
    fun register(verifiedToken: String, username: String, password: String, email: String, phoneNumber: String) {
        viewModelScope.launch {
            _signupState.value = SignupState.Registering
            try {
                println("🔵 Registering user: $username")
                val response = RetrofitInstance.apiService.register(
                    RegisterRequest(verifiedToken, username, password, email, phoneNumber)
                )
                println("🔵 Register Response code: ${response.code()}")
                println("🔵 Register Response body: ${response.body()}")

                if (response.isSuccessful) {
                    val registerResponse = response.body()
                    if (registerResponse != null) {
                        _signupState.value = SignupState.RegistrationSuccess(registerResponse)
                    } else {
                        _signupState.value = SignupState.Error("Registration failed")
                    }
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Unknown error"
                    _signupState.value = SignupState.Error("Registration failed: $errorBody")
                }
            } catch (e: Exception) {
                println("🔴 Register Exception: ${e.message}")
                _signupState.value = SignupState.Error("Network error: ${e.message}")
            }
        }
    }

    fun resetLoginState() {
        _loginState.value = LoginState.Idle
    }

    fun resetSignupState() {
        _signupState.value = SignupState.Idle
    }
}

// Login states (existing)
sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val response: LoginResponse) : LoginState()
    data class Error(val message: String) : LoginState()
}

// New Signup states
sealed class SignupState {
    object Idle : SignupState()
    object GeneratingOtp : SignupState()
    data class OtpGenerated(val tempToken: String) : SignupState()
    object VerifyingOtp : SignupState()
    data class OtpVerified(val verifiedToken: String) : SignupState()
    object Registering : SignupState()
    data class RegistrationSuccess(val response: LoginResponse) : SignupState()
    data class Error(val message: String) : SignupState()
}