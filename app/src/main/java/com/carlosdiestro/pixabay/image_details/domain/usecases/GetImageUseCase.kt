package com.carlosdiestro.pixabay.image_details.domain.usecases

import com.carlosdiestro.pixabay.core.domain.Image
import com.carlosdiestro.pixabay.images.domain.repositories.ImageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetImageUseCase @Inject constructor(
    private val repository: ImageRepository
) {
    operator fun invoke(id: Int): Flow<Image> = repository.getImageById(id)
}