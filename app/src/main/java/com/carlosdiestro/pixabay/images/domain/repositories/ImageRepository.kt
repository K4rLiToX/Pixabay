package com.carlosdiestro.pixabay.images.domain.repositories

import com.carlosdiestro.pixabay.core.domain.Image
import kotlinx.coroutines.flow.Flow

interface ImageRepository {
    fun getImagesByQuery(query: String): Flow<List<Image>>
    fun getImageById(id: Int): Flow<Image>
    suspend fun cacheImagesByQuery(query: String)
}