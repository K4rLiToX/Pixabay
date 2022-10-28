package com.carlosdiestro.pixabay.di

import android.content.Context
import androidx.room.Room
import com.carlosdiestro.pixabay.core.framework.local.ImageDao
import com.carlosdiestro.pixabay.core.framework.local.PixabayDatabase
import com.carlosdiestro.pixabay.core.framework.remote.PixabayApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

private const val BASE_URL = "https://pixabay.com"

@Module
@InstallIn(SingletonComponent::class)
object FrameworkModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): PixabayDatabase {
        return Room.databaseBuilder(
            context,
            PixabayDatabase::class.java,
            "pixabay_db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideImageDao(db: PixabayDatabase): ImageDao = db.imageDao()

    @Singleton
    @Provides
    fun providePixabayApi(): PixabayApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }
}