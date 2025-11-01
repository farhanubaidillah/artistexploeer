package com.example.artistexplorer.service

import com.example.artistexplorer.model.*
import retrofit2.http.GET
import retrofit2.http.Query

interface AudioDbApiService {
    @GET("search.php")
    suspend fun searchArtist(
        @Query("s") artistName: String
    ): ArtistResponse

    @GET("searchalbum.php")
    suspend fun searchAlbums(
        @Query("s") artistName: String
    ): AlbumResponse

    @GET("album.php")
    suspend fun getAlbumDetail(
        @Query("m") albumId: String
    ): AlbumDetailResponse

    @GET("track.php")
    suspend fun getTracks(
        @Query("m") albumId: String
    ): TrackResponse
}
