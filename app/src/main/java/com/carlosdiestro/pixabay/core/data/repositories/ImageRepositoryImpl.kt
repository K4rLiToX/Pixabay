package com.carlosdiestro.pixabay.core.data.repositories

import android.util.Log
import com.carlosdiestro.pixabay.BuildConfig
import com.carlosdiestro.pixabay.core.domain.Image
import com.carlosdiestro.pixabay.core.framework.local.ImageDao
import com.carlosdiestro.pixabay.core.framework.remote.PixabayApi
import com.carlosdiestro.pixabay.images.domain.repositories.ImageRepository
import com.carlosdiestro.pixabay.utils.toDomain
import com.carlosdiestro.pixabay.utils.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val api: PixabayApi,
    private val dao: ImageDao
) : ImageRepository {

    override fun getImagesByQuery(query: String): Flow<List<Image>> {
        return dao.getAll().toDomain()
            .map { images -> images.filter { img -> img.querySearch == query } }
    }

    override fun getImageById(id: Int): Flow<Image> {
        return dao.getById(id).toDomain()
    }

    override suspend fun cacheImagesByQuery(query: String) {
        Log.d("DEBUG", "cacheImagesByQuery: $query")
        val response = try {
            api.getImagesByQuery(BuildConfig.API_KEY, query)
        } catch (e: Exception) {
            return
        }

        if (response.isSuccessful && response.body() != null) {
            Log.d("DEBUG", "cacheImagesByQuery: ${response.body()!!.images}")
            dao.insert(response.body()!!.images.toEntity(query))
        }
    }
}