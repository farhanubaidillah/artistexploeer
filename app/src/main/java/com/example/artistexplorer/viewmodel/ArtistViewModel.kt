package com.example.artistexplorer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.artistexplorer.model.*
import com.example.artistexplorer.repository.AudioDbRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}

class ArtistViewModel : ViewModel() {

    private val repository = AudioDbRepository()

    private val _artistState = MutableStateFlow<UiState<Artist>>(UiState.Loading)
    val artistState: StateFlow<UiState<Artist>> = _artistState

    private val _albumsState = MutableStateFlow<UiState<List<Album>>>(UiState.Loading)
    val albumsState: StateFlow<UiState<List<Album>>> = _albumsState

    private val _albumDetailState = MutableStateFlow<UiState<AlbumDetail>>(UiState.Loading)
    val albumDetailState: StateFlow<UiState<AlbumDetail>> = _albumDetailState

    private val _tracksState = MutableStateFlow<UiState<List<Track>>>(UiState.Loading)
    val tracksState: StateFlow<UiState<List<Track>>> = _tracksState

    fun loadArtistData(artistName: String) {
        viewModelScope.launch {
            try {
                _artistState.value = UiState.Loading
                _albumsState.value = UiState.Loading
                val artistResponse = repository.getArtist(artistName)
                val albumResponse = repository.getAlbums(artistName)

                val artist = artistResponse.artists?.firstOrNull()
                if (artist != null) {
                    _artistState.value = UiState.Success(artist)
                    _albumsState.value = UiState.Success(albumResponse.album ?: emptyList())
                } else {
                    _artistState.value = UiState.Error("Artist not found")
                }
            } catch (e: Exception) {
                _artistState.value = UiState.Error("Connection error: ${e.message}")
                _albumsState.value = UiState.Error("Failed to load albums")
            }
        }
    }

    fun loadAlbumDetail(albumId: String) {
        viewModelScope.launch {
            try {
                _albumDetailState.value = UiState.Loading
                _tracksState.value = UiState.Loading
                val albumDetailResponse = repository.getAlbumDetail(albumId)
                val trackResponse = repository.getTracks(albumId)
                _albumDetailState.value =
                    UiState.Success(albumDetailResponse.album?.firstOrNull() ?: return@launch)
                _tracksState.value = UiState.Success(trackResponse.track ?: emptyList())
            } catch (e: Exception) {
                _albumDetailState.value = UiState.Error("Failed to load album detail")
                _tracksState.value = UiState.Error("Failed to load tracks")
            }
        }
    }
}
