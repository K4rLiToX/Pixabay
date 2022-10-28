package com.carlosdiestro.pixabay.core.domain

class Image(
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