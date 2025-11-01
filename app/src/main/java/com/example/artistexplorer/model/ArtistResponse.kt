package com.example.artistexplorer.model

data class ArtistResponse(
    val artists: List<Artist>?
)

data class Artist(
    val idArtist: String,
    val strArtist: String?,
    val strGenre: String?,
    val strBiographyEN: String?,
    val strArtistThumb: String?,
    val strArtistBanner: String?
)
