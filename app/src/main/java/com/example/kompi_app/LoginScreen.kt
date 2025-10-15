package com.example.kompi_app

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(onLoginClick: () -> Unit) {
    var selectedTab by remember { mutableStateOf("Login") }
    var email by remember { mutableStateOf("grapevinestudiosgh@gmail.com") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .height(280 .dp)// Clean white background
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

            // White Form Content (rest of the screen)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .offset(y = (-40).dp), // Overlap the gradient
                shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 32.dp)
                        .padding(top = 40.dp), // Extra padding to account for overlap
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

                    if (selectedTab == "Login") {
                        // Login Form
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
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color(0xFF0C3B2E),
                                    unfocusedBorderColor = Color(0xFFDDDDDD)
                                ),
                                shape = RoundedCornerShape(12.dp),
                                singleLine = true
                            )

                            // Password Field
                            Text(
                                text = "Password",
                                color = Color(0xFF555555),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            OutlinedTextField(
                                value = password,
                                onValueChange = { password = it },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color(0xFF0C3B2E),
                                    unfocusedBorderColor = Color(0xFFDDDDDD)
                                ),
                                visualTransformation = PasswordVisualTransformation(),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                                shape = RoundedCornerShape(12.dp),
                                singleLine = true
                            )

                            // Forgot password
                            Text(
                                text = "Forgot password!",
                                color = Color(0xFF0C3B2E),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                textAlign = TextAlign.End,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { /* TODO: forgot password */ }
                                    .padding(bottom = 24.dp)
                            )

                            // Login Button
                            Button(
                                onClick = onLoginClick,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(52.dp),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0C3B2E))
                            ) {
                                Text("LOGIN", fontSize = 16.sp, color = Color.White, fontWeight = FontWeight.Bold)
                            }
                        }
                    } else {
                        // Signup placeholder
                        Text(
                            text = "Signup Screen Coming Soon",
                            color = Color.Gray,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(top = 40.dp)
                        )
                    }
                }
            }
        }
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