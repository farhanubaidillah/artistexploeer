package com.example.artistexplorer.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.artistexplorer.model.*
import com.example.artistexplorer.viewmodel.*

@Composable
fun ArtistScreen(
    artistName: String = "John Mayer",
    navController: NavController,
    viewModel: ArtistViewModel = viewModel()
) {
    val artistState by viewModel.artistState.collectAsState()
    val albumsState by viewModel.albumsState.collectAsState()

    LaunchedEffect(Unit) { viewModel.loadArtistData(artistName) }

    when (artistState) {
        is UiState.Loading -> LoadingScreen()
        is UiState.Error -> ErrorScreen((artistState as UiState.Error).message)
        is UiState.Success -> {
            val artist = (artistState as UiState.Success<Artist>).data
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                Text(
                    text = artist.strArtist ?: "",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                artist.strArtistThumb?.let {
                    Image(
                        painter = rememberAsyncImagePainter(it),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .aspectRatio(1.5f),
                        contentScale = ContentScale.Crop
                    )
                }
                Text("Albums", fontWeight = FontWeight.Bold)
                when (albumsState) {
                    is UiState.Loading -> LoadingScreen()
                    is UiState.Error -> ErrorScreen("Albums not found")
                    is UiState.Success -> {
                        val albums = (albumsState as UiState.Success<List<Album>>).data
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(8.dp)
                        ) {
                            items(albums) { album ->
                                Card(
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .clickable {
                                            navController.navigate("albumDetail/${album.idAlbum}")
                                        },
                                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface)
                                ) {
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
                                    Column(Modifier.padding(8.dp)) {
                                        Text(album.strAlbum ?: "", fontWeight = FontWeight.Bold)
                                        Text("${album.intYearReleased ?: ""} • ${album.strGenre ?: ""}")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
