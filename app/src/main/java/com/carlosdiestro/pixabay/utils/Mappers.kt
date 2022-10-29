package com.carlosdiestro.pixabay.utils

import com.carlosdiestro.pixabay.core.data.models.ImageDto
import com.carlosdiestro.pixabay.core.domain.models.Image
import com.carlosdiestro.pixabay.core.framework.local.ImageEntity
import com.carlosdiestro.pixabay.image_details.ui.models.ImagePLO
import com.carlosdiestro.pixabay.images.ui.models.SimpleImagePLO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun Image.toSimplePLO(): SimpleImagePLO = SimpleImagePLO(
    id = id,
    thumbnail = thumbnail,
    userName = userName,
    tags = tags,
    querySearch = querySearch
)

fun List<Image>.toSimplePLO(): List<SimpleImagePLO> = this.map { it.toSimplePLO() }

fun Flow<List<Image>>.toSimplePLO(): Flow<List<SimpleImagePLO>> = this.map { it.toSimplePLO() }

fun ImageDto.toEntity(query: String): ImageEntity = ImageEntity(
    id = id,
    thumbnail = previewURL,
    userName = user,
    tags = tags,
    imageUrl = largeImageURL,
    likes = likes,
    downloads = downloads,
    comments = comments,
    querySearch = query
)

fun List<ImageDto>.toEntity(query: String): List<ImageEntity> = this.map { it.toEntity(query) }

fun ImageEntity.toDomain(): Image = Image(
    id = id,
    thumbnail = thumbnail,
    userName = userName,
    tags = tags,
    imageUrl = imageUrl,
    likes = likes,
    downloads = downloads,
    comments = comments,
    querySearch = querySearch
)

fun List<ImageEntity>.toDomain(): List<Image> = this.map { it.toDomain() }

@JvmName("toDomainImageEntity")
fun Flow<List<ImageEntity>>.toDomain(): Flow<List<Image>> = this.map { it.toDomain() }
fun Flow<ImageEntity>.toDomain(): Flow<Image> = this.map { it.toDomain() }

fun Image.toPLO(): ImagePLO = ImagePLO(
    id = id,
    imageUrl = imageUrl,
    userName = userName,
    tags = tags,
    likes = likes,
    downloads = downloads,
    comments = comments,
    querySearch = querySearch
)