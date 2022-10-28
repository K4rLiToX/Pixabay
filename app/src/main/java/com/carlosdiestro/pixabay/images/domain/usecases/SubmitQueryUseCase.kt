package com.carlosdiestro.pixabay.images.domain.usecases

import com.carlosdiestro.pixabay.images.domain.repositories.ImageRepository
import javax.inject.Inject

class SubmitQueryUseCase @Inject constructor(
    private val repository: ImageRepository
) {

    suspend operator fun invoke(query: String) = repository.cacheImagesByQuery(query.replace(" ", "+"))
}