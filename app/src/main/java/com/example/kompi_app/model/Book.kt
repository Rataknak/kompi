package com.example.kompi_app.model

import androidx.compose.ui.graphics.vector.ImageVector

data class Book(
    val id: Int,
    val title: String,
    val author: String,
    val coverUrl: String,
    val rating: Double,
    val pages: Int,
    val category: String
)

data class Category(
    val name: String,
    val icon: ImageVector
)