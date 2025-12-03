package com.example.kompi_app

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kompi_app.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onLogout: () -> Unit = {},
    onPersonalClick: () -> Unit = {},
    onFavoritesClick: () -> Unit = {},
    onLanguageClick: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            ProfileTopBar()
        },
        containerColor = Color.White
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            item {
                // Profile Header
                ProfileHeader()
                Spacer(modifier = Modifier.height(32.dp))
            }

            item {
                // Menu Items
                ProfileMenuItem(
                    icon = Icons.Outlined.Person,
                    title = "Personal Information",
                    onClick = onPersonalClick
                )
                Divider(color = Color.LightGray.copy(alpha = 0.2f))

                ProfileMenuItem(
                    icon = Icons.Outlined.FavoriteBorder,
                    title = "Favorites",
                    onClick = onFavoritesClick
                )
                Divider(color = Color.LightGray.copy(alpha = 0.2f))

                ProfileMenuItem(
                    icon = Icons.Outlined.Create, // Using Create as a proxy for Language/Translate icon if needed, or default to language specific
                    title = "Language",
                    onClick = onLanguageClick
                )
                Divider(color = Color.LightGray.copy(alpha = 0.2f))

                ProfileMenuItem(
                    icon = Icons.Outlined.Notifications,
                    title = "Notifications",
                    onClick = { /* Navigate to Notifications */ }
                )
                Divider(color = Color.LightGray.copy(alpha = 0.2f))

                ProfileMenuItem(
                    icon = Icons.Outlined.Email, // Using Email or ChatBubble for Contact Support
                    title = "Contact Support",
                    onClick = { /* Contact Support */ }
                )
                Divider(color = Color.LightGray.copy(alpha = 0.2f))

                ProfileMenuItem(
                    icon = Icons.Outlined.Settings,
                    title = "Settings",
                    onClick = { /* Navigate to Settings */ }
                )
                Divider(color = Color.LightGray.copy(alpha = 0.2f))

                Spacer(modifier = Modifier.height(8.dp))

                // Logout
                ProfileMenuItem(
                    icon = Icons.Filled.ExitToApp, // Or a logout icon
                    title = "Log out",
                    textColor = Color(0xFFE53935), // Red color
                    showChevron = false,
                    onClick = onLogout
                )
                Divider(color = Color.LightGray.copy(alpha = 0.2f))
            }
        }
    }
}

@Composable
fun ProfileTopBar() {
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
            // Title
            Text(
                text = "Profile",
                fontSize = 20.sp,
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
fun ProfileHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Avatar
        Image(
            painter = painterResource(id = R.drawable.logo), // Replace with user avatar or placeholder
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(70.dp)
                .clip(CircleShape)
                .background(Color.LightGray),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(16.dp))

        // User Info
        Column {
            Text(
                text = "allen 123213",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "allenash121@gmail.com",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun ProfileMenuItem(
    icon: ImageVector,
    title: String,
    textColor: Color = Color(0xFF333333),
    showChevron: Boolean = true,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = title,
            fontSize = 16.sp,
            color = textColor,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f)
        )

        if (showChevron) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowRight,
                contentDescription = "Navigate",
                tint = Color.Gray,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen()
}
