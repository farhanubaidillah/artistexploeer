package com.example.artistexplorer.model

data class AlbumDetailResponse(
    val album: List<AlbumDetail>?
)

data class AlbumDetail(
    val idAlbum: String,
    val strAlbum: String?,
    val intYearReleased: String?,
    val strGenre: String?,
    val strDescriptionEN: String?,
    val strAlbumThumb: String?
)
