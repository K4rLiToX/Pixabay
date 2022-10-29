package com.carlosdiestro.pixabay.images.domain.usecases

import com.carlosdiestro.pixabay.core.domain.Image
import com.carlosdiestro.pixabay.images.domain.repositories.ImageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetImagesUseCase @Inject constructor(
    private val repository: ImageRepository
) {

    operator fun invoke(query: String): Flow<List<Image>> = repository.getImagesByQuery(query.replace(" ", "+"))
}