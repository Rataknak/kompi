package com.example.kompi_app.screen

import com.example.kompi_app.R

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.draw.clip
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kompi_app.viewmodel.LoginViewModel
import com.example.kompi_app.viewmodel.LoginState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.text.input.VisualTransformation
import com.example.kompi_app.viewmodel.SignupState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onLoginClick: () -> Unit,
    viewModel: LoginViewModel = viewModel()
) {
    var selectedTab by remember { mutableStateOf("Login") }
    var email by remember { mutableStateOf("nimrataknak@gmail.com") }
    var password by remember { mutableStateOf("") }

    // Signup state variables
    var signupEmail by remember { mutableStateOf("") }
    var signupUsername by remember { mutableStateOf("") }
    var signupPhone by remember { mutableStateOf("") }
    var signupPassword by remember { mutableStateOf("") }
    var signupConfirmPassword by remember { mutableStateOf("") }
    var showOtpDialog by remember { mutableStateOf(false) }
    var otpCode by remember { mutableStateOf("") }

    val scrollState = rememberScrollState()
    val loginState by viewModel.loginState.collectAsState()
    val signupState by viewModel.signupState.collectAsState()

    // Handle login success
    LaunchedEffect(loginState) {
        when (loginState) {
            is LoginState.Success -> {
                val userData = (loginState as LoginState.Success).response
                println("✅ Login successful!")
                println("🔑 Token: ${userData.accessToken}")
                println("👤 User: ${userData.username} (${userData.email})")
                println("🎯 Role: ${userData.role}")
                onLoginClick()
            }
            is LoginState.Error -> {
                println("❌ Login error: ${(loginState as LoginState.Error).message}")
            }
            else -> {}
        }
    }

    // Handle signup states
    LaunchedEffect(signupState) {
        when (signupState) {
            is SignupState.OtpGenerated -> {
                showOtpDialog = true
            }
            is SignupState.RegistrationSuccess -> {
                onLoginClick()
            }
            else -> {}
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Top Image with rounded corners
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.down),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp)),
                    contentScale = ContentScale.Crop
                )
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "App Logo",
                    modifier = Modifier
                        .size(200.dp)
                        .align(Alignment.Center)
                )

                // White gradient at bottom of image
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .align(Alignment.BottomCenter)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.White
                                )
                            )
                        )
                )
            }

            // White Form Content
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .offset(y = (-40).dp),
                shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                if (selectedTab == "Login") {
                    // Login Form
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 32.dp)
                            .padding(top = 40.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Logo & title
                        Text(
                            text = "KOMPI",
                            color = Color(0xFF0C3B2E),
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = "Library App",
                            color = Color(0xFF666666),
                            fontSize = 16.sp,
                            modifier = Modifier.padding(bottom = 40.dp)
                        )

                        // Tabs: Login / Signup
                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 32.dp)
                        ) {
                            TabItem("Login", selectedTab == "Login") { selectedTab = "Login" }
                            TabItem("Signup", selectedTab == "Signup") { selectedTab = "Signup" }
                        }

                        // Show loading indicator
                        if (loginState is LoginState.Loading) {
                            CircularProgressIndicator(
                                modifier = Modifier.padding(bottom = 16.dp),
                                color = Color(0xFF0C3B2E)
                            )
                        }

                        // Show error message
                        if (loginState is LoginState.Error) {
                            Text(
                                text = (loginState as LoginState.Error).message,
                                color = Color.Red,
                                fontSize = 14.sp,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                        }

                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            // Email Field
                            Text(
                                text = "E-mail Address",
                                color = Color(0xFF555555),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            OutlinedTextField(
                                value = email,
                                onValueChange = { email = it },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 20.dp),
                                shape = RoundedCornerShape(12.dp),
                                singleLine = true,
                                enabled = loginState !is LoginState.Loading
                            )

                            // Password Field with text toggle
                            Text(
                                text = "Password",
                                color = Color(0xFF555555),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )

                            var passwordVisible by remember { mutableStateOf(false) }

                            OutlinedTextField(
                                value = password,
                                onValueChange = { password = it },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 8.dp),
                                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                                shape = RoundedCornerShape(12.dp),
                                singleLine = true,
                                enabled = loginState !is LoginState.Loading
                            )

                            // Row for Show Password (left) and Forgot Password (right)
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 24.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Show Password checkbox on LEFT
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.clickable { passwordVisible = !passwordVisible }
                                ) {
                                    Checkbox(
                                        checked = passwordVisible,
                                        onCheckedChange = { passwordVisible = it },
                                        colors = CheckboxDefaults.colors(
                                            checkedColor = Color(0xFF0C3B2E),
                                            uncheckedColor = Color(0xFF888888),
                                            checkmarkColor = Color.White
                                        )
                                    )
                                    Text(
                                        text = "Show Password",
                                        color = Color(0xFF555555),
                                        fontSize = 14.sp
                                    )
                                }

                                // Forgot Password on RIGHT
                                Text(
                                    text = "Forgot password!",
                                    color = Color(0xFF0C3B2E),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.clickable { /* TODO: forgot password */ }
                                )
                            }

                            // Login Button
                            Button(
                                onClick = {
                                    viewModel.login(email, password)
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(52.dp),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0C3B2E)),
                                enabled = loginState !is LoginState.Loading && email.isNotEmpty() && password.isNotEmpty()
                            ) {
                                if (loginState is LoginState.Loading) {
                                    CircularProgressIndicator(
                                        color = Color.White,
                                        modifier = Modifier.size(20.dp)
                                    )
                                } else {
                                    Text("LOGIN", fontSize = 16.sp, color = Color.White, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                } else {
                    // Signup Form with all fields and OTP dialog
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState)
                            .padding(horizontal = 32.dp)
                            .padding(top = 40.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Logo & title
                        Text(
                            text = "KOMPI",
                            color = Color(0xFF0C3B2E),
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = "Library App",
                            color = Color(0xFF666666),
                            fontSize = 16.sp,
                            modifier = Modifier.padding(bottom = 40.dp)
                        )

                        // Tabs: Login / Signup
                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 32.dp)
                        ) {
                            TabItem("Login", selectedTab == "Login") { selectedTab = "Login" }
                            TabItem("Signup", selectedTab == "Signup") { selectedTab = "Signup" }
                        }

                        // Show loading indicator for signup
                        if (signupState is SignupState.GeneratingOtp ||
                            signupState is SignupState.VerifyingOtp ||
                            signupState is SignupState.Registering) {
                            CircularProgressIndicator(
                                modifier = Modifier.padding(bottom = 16.dp),
                                color = Color(0xFF0C3B2E)
                            )
                        }

                        // Show error message for signup
                        if (signupState is SignupState.Error) {
                            Text(
                                text = (signupState as SignupState.Error).message,
                                color = Color.Red,
                                fontSize = 14.sp,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                        }

                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            // Email Field
                            Text(
                                text = "E-mail Address",
                                color = Color(0xFF555555),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                OutlinedTextField(
                                    value = signupEmail,
                                    onValueChange = { signupEmail = it },
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(12.dp),
                                    singleLine = true,
                                    placeholder = { Text("Enter your email") },
                                    enabled = !showOtpDialog
                                )
                                Button(
                                    onClick = {
                                        viewModel.generateOtp(signupEmail)
                                    },
                                    modifier = Modifier
                                        .height(56.dp)
                                        .width(100.dp),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0C3B2E)),
                                    enabled = signupEmail.isNotEmpty() && !showOtpDialog
                                ) {
                                    Text(
                                        text = "Get OTP",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }

                            // Username Field
                            Text(
                                text = "Username",
                                color = Color(0xFF555555),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )
                            OutlinedTextField(
                                value = signupUsername,
                                onValueChange = { signupUsername = it },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                singleLine = true,
                                placeholder = { Text("Enter username") },
                                enabled = !showOtpDialog
                            )

                            // Phone Number Field
                            Text(
                                text = "Phone Number",
                                color = Color(0xFF555555),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )
                            OutlinedTextField(
                                value = signupPhone,
                                onValueChange = { signupPhone = it },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                singleLine = true,
                                placeholder = { Text("Enter phone number") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                                enabled = !showOtpDialog
                            )

                            // Password Field
                            Text(
                                text = "Password",
                                color = Color(0xFF555555),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )
                            OutlinedTextField(
                                value = signupPassword,
                                onValueChange = { signupPassword = it },
                                modifier = Modifier.fillMaxWidth(),
                                visualTransformation = PasswordVisualTransformation(),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                                shape = RoundedCornerShape(12.dp),
                                singleLine = true,
                                placeholder = { Text("Create password") },
                                enabled = !showOtpDialog
                            )

                            // Confirm Password Field
                            Text(
                                text = "Confirm Password",
                                color = Color(0xFF555555),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )
                            OutlinedTextField(
                                value = signupConfirmPassword,
                                onValueChange = { signupConfirmPassword = it },
                                modifier = Modifier.fillMaxWidth(),
                                visualTransformation = PasswordVisualTransformation(),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                                shape = RoundedCornerShape(12.dp),
                                singleLine = true,
                                placeholder = { Text("Confirm password") },
                                enabled = !showOtpDialog
                            )

                            // Terms and Conditions
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "By signing up, you agree to our ",
                                    color = Color(0xFF666666),
                                    fontSize = 12.sp
                                )
                                Text(
                                    text = "Terms & Conditions",
                                    color = Color(0xFF0C3B2E),
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.clickable { /* TODO: Show terms */ }
                                )
                            }

                            // Sign Up Button
                            Button(
                                onClick = {
                                    val verifiedToken = when (signupState) {
                                        is SignupState.OtpVerified -> (signupState as SignupState.OtpVerified).verifiedToken
                                        else -> ""
                                    }
                                    if (verifiedToken.isNotEmpty() && signupUsername.isNotEmpty() &&
                                        signupPassword.isNotEmpty() && signupConfirmPassword.isNotEmpty() &&
                                        signupPhone.isNotEmpty() && signupPassword == signupConfirmPassword) {
                                        viewModel.register(verifiedToken, signupUsername, signupPassword, signupEmail, signupPhone)
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(52.dp),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (signupState is SignupState.OtpVerified) Color(0xFF0C3B2E) else Color(0xFFCCCCCC)
                                ),
                                enabled = signupState is SignupState.OtpVerified
                            ) {
                                Text(
                                    "SIGN UP",
                                    fontSize = 16.sp,
                                    color = if (signupState is SignupState.OtpVerified) Color.White else Color(0xFF888888),
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            // Already have account
                            Text(
                                text = "Already have an account? Login",
                                color = Color(0xFF0C3B2E),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        selectedTab = "Login"
                                        viewModel.resetSignupState()
                                        showOtpDialog = false
                                    }
                                    .padding(top = 16.dp, bottom = 32.dp)
                            )
                        }
                    }
                }
            }
        }
    }

    // OTP Verification Dialog
    if (showOtpDialog) {
        AlertDialog(
            onDismissRequest = {
                if (signupState !is SignupState.VerifyingOtp) {
                    showOtpDialog = false
                }
            },
            title = {
                Text(
                    text = "Verify OTP",
                    color = Color(0xFF0C3B2E),
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column {
                    Text(
                        text = "Enter the OTP sent to your email",
                        color = Color(0xFF666666),
                        fontSize = 14.sp,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    OutlinedTextField(
                        value = otpCode,
                        onValueChange = { otpCode = it },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true,
                        placeholder = { Text("Enter OTP code") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val tempToken = when (signupState) {
                            is SignupState.OtpGenerated -> (signupState as SignupState.OtpGenerated).tempToken
                            else -> ""
                        }
                        if (tempToken.isNotEmpty() && otpCode.isNotEmpty()) {
                            viewModel.verifyOtp(tempToken, otpCode)
                            showOtpDialog = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0C3B2E)),
                    enabled = otpCode.isNotEmpty() && signupState !is SignupState.VerifyingOtp
                ) {
                    Text("VERIFY OTP")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        if (signupState !is SignupState.VerifyingOtp) {
                            showOtpDialog = false
                        }
                    }
                ) {
                    Text("CANCEL")
                }
            }
        )
    }
}

@Composable
fun TabItem(text: String, selected: Boolean, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { onClick() }
            .padding(horizontal = 20.dp)
    ) {
        Text(
            text = text,
            color = if (selected) Color(0xFF0C3B2E) else Color(0xFF888888),
            fontSize = 18.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
            letterSpacing = 0.5.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        if (selected) {
            Box(
                modifier = Modifier
                    .height(4.dp)
                    .width(70.dp)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(Color(0xFF0C3B2E), Color(0xFF1A4F3E))
                        ),
                        shape = RoundedCornerShape(2.dp)
                    )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(onLoginClick = {})
}