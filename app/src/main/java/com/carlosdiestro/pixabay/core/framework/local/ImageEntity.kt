package com.carlosdiestro.pixabay.core.framework.local

import androidx.room.Entity

@Entity(
    tableName = "images_table",
    primaryKeys = ["id", "querySearch"]
)
data class ImageEntity(
    val id: Int,
    val thumbnail: String,
    val userName: String,
    val tags: String,
    val imageUrl: String,
    val likes: Int,
    val downloads: Int,
    val comments: Int,
    val querySearch: String
)