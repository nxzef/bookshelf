package com.example.bookshelf.data.network

import com.example.bookshelf.data.model.Volumes
import retrofit2.http.GET
import retrofit2.http.Query

interface BookApiService {

    @GET("volumes")
    suspend fun getVolumes(
        @Query("q", encoded = true) query: String,
    ): Volumes

}