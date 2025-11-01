package com.example.artistexplorer.repository

import com.example.artistexplorer.model.*
import com.example.artistexplorer.service.AudioDbApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AudioDbRepository {

    private val api: AudioDbApiService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.theaudiodb.com/api/v1/json/123/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        api = retrofit.create(AudioDbApiService::class.java)
    }

    suspend fun getArtist(artistName: String) = api.searchArtist(artistName)
    suspend fun getAlbums(artistName: String) = api.searchAlbums(artistName)
    suspend fun getAlbumDetail(albumId: String) = api.getAlbumDetail(albumId)
    suspend fun getTracks(albumId: String) = api.getTracks(albumId)
}
