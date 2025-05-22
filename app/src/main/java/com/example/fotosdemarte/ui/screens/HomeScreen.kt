package com.example.fotosdemarte.ui.screens

import android.R.attr.onClick
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.fotosdemarte.R
import com.example.fotosdemarte.model.MarsPhoto
import androidx.compose.material3.Button

@Composable
fun HomeScreen(
    marsUiState: MarsUiState,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    when (marsUiState) {
        is MarsUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is MarsUiState.Success -> ResultScreen(
            photos = marsUiState.photos,
            modifier = modifier
                .fillMaxSize()
                .padding(contentPadding)
        )
        is MarsUiState.Error -> ErrorScreen(onRetry = onRetry, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun ResultScreen(
    photos: List<MarsPhoto>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        // DEBUG: mostra quantas fotos vieram
        Text(
            text = "Fotos encontradas: ${photos.size}",
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        if (photos.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Nenhuma foto encontrada.")
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(photos) { photo ->
                    Log.d("MARS_URL", photo.imgSrc)

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .padding(4.dp)
                            .background(Color.DarkGray) // placeholder
                    ) {
                        AsyncImage(
                            model = photo.imgSrc,
                            contentDescription = "Mars photo ${photo.id}",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.matchParentSize()
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier,
    onRetry: () -> Unit,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = stringResource(R.string.loading_failed))
        Button(onClick = onRetry) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = stringResource(R.string.loading))
    }
}

@Preview(showBackground = true)
@Composable
fun ResultScreenPreview() {
    val samplePhotos = listOf(
        MarsPhoto(id = "1", imgSrc = "https://via.placeholder.com/150"),
        MarsPhoto(id = "2", imgSrc = "https://via.placeholder.com/150")
    )
    ResultScreen(
        photos = samplePhotos,
        modifier = Modifier.fillMaxSize()
    )
}

