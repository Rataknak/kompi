@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.kompi_app.screen

import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.kompi_app.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalScreen(onBackClick: () -> Unit = {}) {
    // 1. Add state for editable fields
    var username by remember { mutableStateOf("Username") }
    // Email state (read-only in UI)
    var email by remember { mutableStateOf("allen112313212@gmail.com") }

    // Birth date parts
    var day by remember { mutableStateOf("11") }
    var month by remember { mutableStateOf("May") }
    var year by remember { mutableStateOf("2003") }

    // Image picker state
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    var bitmap by remember { mutableStateOf<ImageBitmap?>(null) }

    // Load bitmap asynchronously
    LaunchedEffect(selectedImageUri) {
        if (selectedImageUri != null) {
            withContext(Dispatchers.IO) {
                try {
                    val loadedBitmap = if (Build.VERSION.SDK_INT < 28) {
                        @Suppress("DEPRECATION")
                        MediaStore.Images.Media.getBitmap(context.contentResolver, selectedImageUri)
                    } else {
                        val source = ImageDecoder.createSource(context.contentResolver, selectedImageUri!!)
                        ImageDecoder.decodeBitmap(source)
                    }
                    bitmap = loadedBitmap.asImageBitmap()
                } catch (e: Exception) {
                    e.printStackTrace()
                    bitmap = null
                }
            }
        }
    }

    // Launcher for Photo Picker
    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> selectedImageUri = uri }
    )

    var showDatePicker by remember { mutableStateOf(false) }

    if (showDatePicker) {
        WheelDatePickerDialog(
            initialDay = day.toIntOrNull() ?: 1,
            initialMonth = month,
            initialYear = year.toIntOrNull() ?: 2003,
            onDateSelected = { d, m, y ->
                day = d.toString()
                month = m
                year = y.toString()
                showDatePicker = false
            },
            onDismiss = { showDatePicker = false }
        )
    }

    Scaffold(
        topBar = {
            PersonalTopBar(onBackClick = onBackClick)
        },
        containerColor = Color.White,
        bottomBar = {
            // Save Button fixed at the bottom
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shadowElevation = 8.dp
            ) {
                Button(
                    onClick = { /* Save Action */ },
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF0C3B2E)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Save",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // Profile Picture Section
            Box(
                contentAlignment = Alignment.BottomEnd,
                modifier = Modifier
                    .size(110.dp)
                    .clickable {
                        singlePhotoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }
            ) {
                // The Image
                if (bitmap != null) {
                    Image(
                        bitmap = bitmap!!,
                        contentDescription = "Profile Photo",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                            .background(Color.LightGray),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "Profile Photo",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                            .background(Color.LightGray),
                        contentScale = ContentScale.Crop
                    )
                }

                // Edit Icon Overlay
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(Color(0xFF0C3B2E), CircleShape)
                        .border(2.dp, Color.White, CircleShape)
                        .padding(6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Photo",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Username666",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333)
            )

            Text(
                text = "Change Photo",
                fontSize = 12.sp,
                color = Color.Gray,
                modifier = Modifier
                    .clickable {
                        singlePhotoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }
                    .padding(8.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // 2. Pass state and callbacks to fields

            // Full Name Field (Editable)
            EditablePersonalInfoField(
                label = "Full Name",
                value = username,
                onValueChange = { username = it },
                icon = Icons.Outlined.Person
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Email Field (Read-only)
            ReadOnlyPersonalInfoField(
                label = "Email Address",
                value = email,
                icon = Icons.Outlined.Email
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Birth Date
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Birth Date",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                // Clickable Date Row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showDatePicker = true }, // Open Picker on Click
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Display Date Parts (Non-editable directly, but clickable via parent)
                    DateDisplayBox(value = day, modifier = Modifier.weight(1f))
                    Spacer(modifier = Modifier.width(16.dp))
                    DateDisplayBox(value = month, modifier = Modifier.weight(1f))
                    Spacer(modifier = Modifier.width(16.dp))
                    DateDisplayBox(value = year, modifier = Modifier.weight(1f))
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Joined 15 Oct 2025",
                fontSize = 12.sp,
                color = Color.LightGray
            )

            // Add extra space for the bottom bar
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
fun PersonalTopBar(onBackClick: () -> Unit) {
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
                text = "Personal Information",
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
fun EditablePersonalInfoField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    icon: ImageVector
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))

            // Editable Text Field
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                textStyle = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF333333)
                ),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                cursorBrush = SolidColor(Color(0xFF0C3B2E))
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Divider(color = Color.LightGray.copy(alpha = 0.3f), thickness = 1.dp)
    }
}

@Composable
fun ReadOnlyPersonalInfoField(
    label: String,
    value: String,
    icon: ImageVector
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = value,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF333333)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Divider(color = Color.LightGray.copy(alpha = 0.3f), thickness = 1.dp)
    }
}

@Composable
fun DateDisplayBox(
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.padding(vertical = 8.dp)) {
            Text(
                text = value,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF333333),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Divider(color = Color.LightGray.copy(alpha = 0.3f), thickness = 1.dp)
    }
}

@Composable
fun WheelDatePickerDialog(
    initialDay: Int,
    initialMonth: String,
    initialYear: Int,
    onDateSelected: (Int, String, Int) -> Unit,
    onDismiss: () -> Unit
) {
    val months = listOf("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December")
    val years = (1960..2026).toList()
    val days = (1..31).toList()

    // Temporary state for the dialog
    var selectedDay by remember { mutableStateOf(initialDay) }
    var selectedMonth by remember { mutableStateOf(initialMonth) }
    var selectedYear by remember { mutableStateOf(initialYear) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Select Date",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0C3B2E),
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Row(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    // Day Picker
                    WheelPicker(
                        items = days,
                        initialItem = selectedDay,
                        onItemSelected = { selectedDay = it },
                        modifier = Modifier.weight(1f)
                    )
                    // Month Picker
                    WheelPicker(
                        items = months,
                        initialItem = selectedMonth,
                        onItemSelected = { selectedMonth = it },
                        modifier = Modifier.weight(1.5f)
                    )
                    // Year Picker
                    WheelPicker(
                        items = years,
                        initialItem = selectedYear,
                        onItemSelected = { selectedYear = it },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancel", color = Color.Gray)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = { onDateSelected(selectedDay, selectedMonth, selectedYear) },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0C3B2E))
                    ) {
                        Text("OK")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T> WheelPicker(
    items: List<T>,
    initialItem: T,
    onItemSelected: (T) -> Unit,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()
    val flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)
    val itemHeight = 40.dp

    // Find initial index
    val initialIndex = remember { items.indexOf(initialItem).coerceAtLeast(0) }

    LaunchedEffect(Unit) {
        listState.scrollToItem(initialIndex)
    }

    // Track scroll to update selection
    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .map { index ->
                val layoutInfo = listState.layoutInfo
                val visibleItems = layoutInfo.visibleItemsInfo
                if (visibleItems.isNotEmpty()) {
                    val centerOffset = layoutInfo.viewportStartOffset + (layoutInfo.viewportEndOffset - layoutInfo.viewportStartOffset) / 2
                    visibleItems.minByOrNull { kotlin.math.abs(it.offset + it.size / 2 - centerOffset) }?.index ?: index
                } else index
            }
            .distinctUntilChanged()
            .collect { index ->
                if (index in items.indices) {
                    onItemSelected(items[index])
                }
            }
    }

    Box(
        modifier = modifier.height(200.dp),
        contentAlignment = Alignment.Center
    ) {
        // Selection Indicator overlay
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(itemHeight)
                .background(Color(0xFF0C3B2E).copy(alpha = 0.1f), RoundedCornerShape(8.dp))
        )

        LazyColumn(
            state = listState,
            flingBehavior = flingBehavior,
            contentPadding = PaddingValues(vertical = 80.dp), // Half of container height - half of item height
            modifier = Modifier.fillMaxHeight()
        ) {
            itemsIndexed(items) { _, item ->
                Box(
                    modifier = Modifier
                        .height(itemHeight)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = item.toString(),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF333333),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PersonalScreenPreview() {
    PersonalScreen()
}
