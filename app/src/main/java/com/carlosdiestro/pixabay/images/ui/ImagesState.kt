package com.carlosdiestro.pixabay.images.ui

import com.carlosdiestro.pixabay.images.ui.models.SimpleImagePLO



data class ImagesState(
    val images: List<SimpleImagePLO> = emptyList()
)

sealed interface ImagesEvent {
    class SubmitQuery(val value: String) : ImagesEvent
}