package com.carlosdiestro.pixabay.core.framework.remote

import com.carlosdiestro.pixabay.core.data.models.PixabayResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayApi {

    @GET("/api/")
    suspend fun getImagesByQuery(
        @Query("key") key: String,
        @Query("q") query: String
    ): PixabayResponse
}