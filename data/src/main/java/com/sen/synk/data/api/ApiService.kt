package com.sen.synk.data.api

import com.sen.synk.data.model.AlbumResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("/photos")
    suspend fun fetchAlbum(
        @Query("_page") page: Int,
        @Query("_limit") limit: Int = 1
    ): AlbumResponse
}