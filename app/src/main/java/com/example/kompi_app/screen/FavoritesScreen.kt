package com.example.kompi_app

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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class FavoriteBook(
    val id: Int,
    val title: String,
    val author: String,
    val rating: String,
    val reviews: String,
    val coverRes: Int,
    val isFavorite: MutableState<Boolean> = mutableStateOf(true)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(onBackClick: () -> Unit = {}) {
    val favoriteBooks = remember {
        listOf(
            FavoriteBook(1, "The Steal Like An Artist", "Austin Kleon", "5.0", "23k Reviews", R.drawable.book1),
            FavoriteBook(2, "The Steal Like An Artist", "Austin Kleon", "5.0", "22k Reviews", R.drawable.book1),
            FavoriteBook(3, "The Steal Like An Artist", "Austin Kleon", "5.0", "22k Reviews", R.drawable.book1),
            FavoriteBook(4, "The Steal Like An Artist", "Austin Kleon", "5.0", "23k Reviews", R.drawable.book1),
            FavoriteBook(5, "The Steal Like An Artist", "Austin Kleon", "5.0", "22k Reviews", R.drawable.book1),
            FavoriteBook(6, "The Steal Like An Artist", "Austin Kleon", "5.0", "22k Reviews", R.drawable.book1),
            FavoriteBook(7, "The Steal Like An Artist", "Austin Kleon", "5.0", "22k Reviews", R.drawable.book1),
            FavoriteBook(8, "The Steal Like An Artist", "Austin Kleon", "5.0", "22k Reviews", R.drawable.book1),
            FavoriteBook(9, "The Steal Like An Artist", "Austin Kleon", "5.0", "22k Reviews", R.drawable.book1),
            FavoriteBook(10, "The Steal Like An Artist", "Austin Kleon", "5.0", "22k Reviews", R.drawable.book1)
        )
    }

    Scaffold(
        topBar = {
            FavoritesTopBar(onBackClick = onBackClick)
        },
        containerColor = Color.White
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp)
        ) {
            items(favoriteBooks) { book ->
                FavoriteBookItem(book)
                Divider(
                    color = Color.LightGray.copy(alpha = 0.3f),
                    thickness = 1.dp,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}

@Composable
fun FavoritesTopBar(onBackClick: () -> Unit) {
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
                text = "Favorites",
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
fun FavoriteBookItem(book: FavoriteBook) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        // Book Cover
        Image(
            painter = painterResource(id = book.coverRes),
            contentDescription = book.title,
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
                    text = book.title,
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
                        tint = if (book.isFavorite.value) Color.Red else Color.Gray,
                        modifier = Modifier
                            .size(22.dp)
                            .clickable {
                                book.isFavorite.value = !book.isFavorite.value
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
                text = book.author,
                fontSize = 12.sp,
                color = Color.Gray
            )
            
            Spacer(modifier = Modifier.height(8.dp))
             
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = Color(0xFFFF7043), fontWeight = FontWeight.Bold)) {
                        append(book.rating)
                    }
                    withStyle(style = SpanStyle(color = Color.Gray)) {
                        append(" | Based on ${book.reviews}")
                    }
                },
                fontSize = 11.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FavoritesScreenPreview() {
    FavoritesScreen()
}
