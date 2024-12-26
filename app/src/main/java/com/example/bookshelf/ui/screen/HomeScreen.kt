package com.example.bookshelf.ui.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.bookshelf.R
import com.example.bookshelf.data.model.BookItem
import com.example.bookshelf.data.model.BookResponse

@Composable
fun HomeScreen(
    bookResponse: BookResponse,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .padding(horizontal = 8.dp),
        contentPadding = contentPadding
    ) {
        items(bookResponse.items.orEmpty()) { book ->
            Book(
                book = book
            )
        }
    }
}

@Composable
fun Book(
    book: BookItem,
    modifier: Modifier = Modifier
        .padding(vertical = 8.dp)
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.medium
    ) {
        Text(book.volumeInfo.title, overflow = TextOverflow.Ellipsis)
        Text(book.volumeInfo.subtitle ?: "")
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(
                book.volumeInfo.imageLinks?.thumbnail?.replace("http://", "https://")
            ).crossfade(true).build(),
            error = painterResource(R.drawable.ic_connection_error),
            placeholder = painterResource(R.drawable.loading_img),
            contentDescription = book.volumeInfo.title,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .height(200.dp)
        )
    }
}