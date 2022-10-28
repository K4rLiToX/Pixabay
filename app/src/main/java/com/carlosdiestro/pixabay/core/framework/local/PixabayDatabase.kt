package com.carlosdiestro.pixabay.core.framework.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        ImageEntity::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class PixabayDatabase : RoomDatabase() {
    abstract fun imageDao(): ImageDao
}