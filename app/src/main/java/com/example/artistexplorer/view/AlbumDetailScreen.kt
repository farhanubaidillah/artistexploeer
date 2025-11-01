package com.example.artistexplorer.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.artistexplorer.viewmodel.*

@Composable
fun AlbumDetailScreen(albumId: String, viewModel: ArtistViewModel = viewModel()) {
    val albumDetailState by viewModel.albumDetailState.collectAsState()
    val tracksState by viewModel.tracksState.collectAsState()

    LaunchedEffect(Unit) { viewModel.loadAlbumDetail(albumId) }

    when (albumDetailState) {
        is UiState.Loading -> LoadingScreen()
        is UiState.Error -> ErrorScreen("Failed to load album")
        is UiState.Success -> {
            val album = (albumDetailState as UiState.Success).data
            Column(Modifier.fillMaxSize().padding(8.dp)) {
                Text(album.strAlbum ?: "", fontWeight = FontWeight.Bold)
                album.strAlbumThumb?.let {
                    Image(
                        painter = rememberAsyncImagePainter(it),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f),
                        contentScale = ContentScale.Crop
                    )
                }
                Text("${album.intYearReleased} • ${album.strGenre}")
                Spacer(Modifier.height(8.dp))
                Text(album.strDescriptionEN ?: "", style = MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.height(8.dp))
                Text("Tracks", fontWeight = FontWeight.Bold)
                when (tracksState) {
                    is UiState.Loading -> LoadingScreen()
                    is UiState.Error -> ErrorScreen("Tracks not found")
                    is UiState.Success -> {
                        val tracks = (tracksState as UiState.Success).data
                        LazyColumn {
                            items(tracks.size) { i ->
                                val track = tracks[i]
                                Text("${i + 1}. ${track.strTrack ?: ""}")
                            }
                        }
                    }
                }
            }
        }
    }
}
