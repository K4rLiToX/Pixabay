package com.carlosdiestro.pixabay.di

import com.carlosdiestro.pixabay.core.data.repositories.ImageRepositoryImpl
import com.carlosdiestro.pixabay.images.domain.repositories.ImageRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Singleton
    @Binds
    abstract fun bindImageRepository(repository: ImageRepositoryImpl): ImageRepository
}