package com.example.kompi_app.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.kompi_app.R

data class NotificationItem(
    val id: Int,
    val title: String,
    val author: String,
    val status: String,
    val coverRes: Int,
    val isFavorite: MutableState<Boolean> = mutableStateOf(false)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen(onBackClick: () -> Unit = {}) {
    // Handle system back button
    BackHandler {
        onBackClick()
    }

    val notifications = remember {
        listOf(
            NotificationItem(1, "The Steal Like An Artist", "Austin Kleon", "Available 17 Oct 2025", R.drawable.book1),
            NotificationItem(2, "The Steal Like An Artist", "Austin Kleon", "Coming Soon", R.drawable.book1),
            NotificationItem(3, "The Steal Like An Artist", "Austin Kleon", "Coming Soon", R.drawable.book1),
            NotificationItem(4, "The Steal Like An Artist", "Austin Kleon", "Coming Soon", R.drawable.book1),
            NotificationItem(5, "The Steal Like An Artist", "Austin Kleon", "Coming Soon", R.drawable.book1),
            NotificationItem(6, "The Steal Like An Artist", "Austin Kleon", "Available 11 Oct 2025", R.drawable.book1)
        )
    }

    Scaffold(
        topBar = {
            NotificationTopBar(onBackClick = onBackClick)
        },
        containerColor = Color.White
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp)
        ) {
            items(notifications) { notification ->
                if (notification.id == 6) {
                     // Insert Announcement before the last item (as per image visual flow)
                     AnnouncementItem()
                     Spacer(modifier = Modifier.height(16.dp))
                     Divider(
                        color = Color.LightGray.copy(alpha = 0.3f),
                        thickness = 1.dp,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }

                NotificationItemRow(notification)

                if (notification.id != 6) {
                    Divider(
                        color = Color.LightGray.copy(alpha = 0.3f),
                        thickness = 1.dp,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationTopBar(onBackClick: () -> Unit) {
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
                text = "Notifications",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333),
                modifier = Modifier.align(Alignment.Center),
                textAlign = TextAlign.Center
            )

            // Back Button - Using Surface for robust click handling
            Surface(
                onClick = onBackClick,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .zIndex(1f)
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
        }
        Divider(color = Color.LightGray.copy(alpha = 0.3f), thickness = 1.dp)
    }
}

@Composable
fun NotificationItemRow(notification: NotificationItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        // Book Cover
        Image(
            painter = painterResource(id = notification.coverRes),
            contentDescription = notification.title,
            modifier = Modifier
                .width(80.dp)
                .height(110.dp)
                .clip(RoundedCornerShape(4.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Content
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 4.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = notification.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2E3A59),
                    modifier = Modifier.weight(1f)
                )
                
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                     Icon(
                        imageVector = Icons.Filled.Favorite, 
                        contentDescription = "Favorite",
                        tint = if (notification.isFavorite.value) Color.Red else Color.Gray,
                        modifier = Modifier
                            .size(22.dp)
                            .clickable { 
                                notification.isFavorite.value = !notification.isFavorite.value 
                            }
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.Filled.MoreVert,
                        contentDescription = "More",
                        tint = Color.Gray,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = notification.author,
                fontSize = 12.sp,
                color = Color.Gray
            )
            
            Spacer(modifier = Modifier.weight(1f)) // Push status to bottom
             
            Text(
                text = notification.status,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFFD97B73), // Reddish color from image
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}

@Composable
fun AnnouncementItem() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Upcoming Update Announcement",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFD35400) // Orange/Rust color
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "We're thrilled to announce that our next major update will be released on 16 October 2025. This update includes enhanced performance, bug fixes, and several new features designed to improve usability and reliability.\nThank you for your continued support — we can't wait for you to experience what's next!",
            fontSize = 11.sp,
            color = Color.Gray,
            lineHeight = 16.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NotificationScreenPreview() {
    NotificationScreen()
}
