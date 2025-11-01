package com.example.artistexplorer.model

data class TrackResponse(
    val track: List<Track>?
)

data class Track(
    val idTrack: String,
    val strTrack: String?,
    val intDuration: String?
)
