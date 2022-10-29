package com.carlosdiestro.pixabay.core.data.models

import com.google.gson.annotations.SerializedName

data class PixabayResponse(
    @SerializedName("hits")
    val images: List<ImageDto>,
    @SerializedName("total")
    val total: Int,
    @SerializedName("totalHits")
    val totalHits: Int
)