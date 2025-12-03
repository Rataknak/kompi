package com.example.kompi_app.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kompi_app.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageScreen(onBackClick: () -> Unit = {}) {
    var selectedLanguage by remember { mutableStateOf("English") }

    Scaffold(
        topBar = {
            LanguageTopBar(onBackClick = onBackClick)
        },
        containerColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            LanguageItem(
                name = "English",
                flagRes = R.drawable.english, // Assuming you have this resource
                isSelected = selectedLanguage == "English",
                onClick = { selectedLanguage = "English" }
            )

            Spacer(modifier = Modifier.height(16.dp))
            Divider(color = Color.LightGray.copy(alpha = 0.3f), thickness = 1.dp)
            Spacer(modifier = Modifier.height(16.dp))

            LanguageItem(
                name = "Cambodia",
                flagRes = R.drawable.cambodia, // Assuming you have this resource
                isSelected = selectedLanguage == "Cambodia",
                onClick = { selectedLanguage = "Cambodia" }
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            Divider(color = Color.LightGray.copy(alpha = 0.3f), thickness = 1.dp)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageTopBar(onBackClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            // Back Button
            Surface(
                onClick = onBackClick,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .size(40.dp),
                shape = CircleShape,
                color = Color(0xFFF5F5F5)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.Black,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            // Title
            Text(
                text = "Language",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333),
                modifier = Modifier.align(Alignment.Center),
                textAlign = TextAlign.Center
            )
        }
        Divider(color = Color.LightGray.copy(alpha = 0.3f), thickness = 1.dp)
    }
}

@Composable
fun LanguageItem(
    name: String,
    flagRes: Int,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Flag
        Image(
            painter = painterResource(id = flagRes),
            contentDescription = "$name Flag",
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Language Name
        Text(
            text = name,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF333333),
            modifier = Modifier.weight(1f)
        )

        // Checkmark if selected
        if (isSelected) {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = "Selected",
                tint = Color(0xFF007AFF), // Blue color
                modifier = Modifier
                    .size(20.dp)
                    .background(Color(0xFF007AFF).copy(alpha = 0.1f), CircleShape)
                    .padding(2.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LanguageScreenPreview() {
    LanguageScreen()
}
