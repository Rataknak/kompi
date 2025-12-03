package com.example.kompi_app.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(onBackClick: () -> Unit = {}) {
    var areNotificationsEnabled by remember { mutableStateOf(true) }
    var isDarkModeEnabled by remember { mutableStateOf(false) }
    var areUpdatesEnabled by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            SettingTopBar(onBackClick = onBackClick)
        },
        containerColor = Color.White
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp),
            contentPadding = PaddingValues(top = 24.dp, bottom = 24.dp)
        ) {
            // General Section
            item {
                SectionHeader(title = "General")
                Spacer(modifier = Modifier.height(16.dp))

                // Toggle Item: Notifications
                SettingSwitchItem(
                    icon = Icons.Outlined.Notifications,
                    title = "Push Notifications",
                    checked = areNotificationsEnabled,
                    onCheckedChange = { areNotificationsEnabled = it }
                )
                Divider(color = Color.LightGray.copy(alpha = 0.2f))

                // Toggle Item: Dark Mode
                SettingSwitchItem(
                    icon = Icons.Outlined.Nightlight,
                    title = "Dark Mode",
                    checked = isDarkModeEnabled,
                    onCheckedChange = { isDarkModeEnabled = it }
                )
                Divider(color = Color.LightGray.copy(alpha = 0.2f))

                // Toggle Item: Auto Updates
                SettingSwitchItem(
                    icon = Icons.Outlined.SystemUpdate,
                    title = "Auto Updates",
                    checked = areUpdatesEnabled,
                    onCheckedChange = { areUpdatesEnabled = it }
                )
                Divider(color = Color.LightGray.copy(alpha = 0.2f))

                Spacer(modifier = Modifier.height(32.dp))
            }

            // Account & Security Section
            item {
                SectionHeader(title = "Account & Security")
                Spacer(modifier = Modifier.height(16.dp))

                // Navigation Item: Change Password
                SettingNavigationItem(
                    icon = Icons.Outlined.Lock,
                    title = "Change Password",
                    onClick = { /* Navigate to Change Password */ }
                )
                Divider(color = Color.LightGray.copy(alpha = 0.2f))

                // Navigation Item: Privacy Policy
                SettingNavigationItem(
                    icon = Icons.Outlined.PrivacyTip,
                    title = "Privacy Policy",
                    onClick = { /* Navigate to Privacy Policy */ }
                )
                Divider(color = Color.LightGray.copy(alpha = 0.2f))

                // Navigation Item: Terms of Service
                SettingNavigationItem(
                    icon = Icons.Outlined.Description,
                    title = "Terms of Service",
                    onClick = { /* Navigate to ToS */ }
                )
                Divider(color = Color.LightGray.copy(alpha = 0.2f))

                Spacer(modifier = Modifier.height(32.dp))
            }

            // Danger Zone
            item {
                SectionHeader(title = "Danger Zone")
                Spacer(modifier = Modifier.height(16.dp))

                // Action Item: Delete Account
                SettingActionItem(
                    icon = Icons.Outlined.Delete,
                    title = "Delete Account",
                    textColor = Color(0xFFE53935),
                    onClick = { /* Show confirmation dialog */ }
                )
            }
        }
    }
}

@Composable
fun SettingTopBar(onBackClick: () -> Unit) {
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
            Box(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFF5F5F5))
                    .clickable { onBackClick() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black,
                    modifier = Modifier.size(20.dp)
                )
            }

            // Title
            Text(
                text = "Settings",
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
fun SectionHeader(title: String) {
    Text(
        text = title,
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color.Gray,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun SettingSwitchItem(
    icon: ImageVector,
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
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
            fontWeight = FontWeight.Medium,
            color = Color(0xFF333333),
            modifier = Modifier.weight(1f)
        )

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = Color(0xFF0C3B2E),
                checkedBorderColor = Color(0xFF0C3B2E),
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = Color(0xFFE0E0E0),
                uncheckedBorderColor = Color(0xFFE0E0E0)
            )
        )
    }
}

@Composable
fun SettingNavigationItem(
    icon: ImageVector,
    title: String,
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
            fontWeight = FontWeight.Medium,
            color = Color(0xFF333333),
            modifier = Modifier.weight(1f)
        )

        Icon(
            imageVector = Icons.Filled.KeyboardArrowRight,
            contentDescription = "Navigate",
            tint = Color.Gray,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun SettingActionItem(
    icon: ImageVector,
    title: String,
    textColor: Color,
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
            tint = textColor,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = textColor,
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingScreenPreview() {
    SettingScreen()
}
