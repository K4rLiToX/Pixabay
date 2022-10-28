package com.carlosdiestro.pixabay.core.framework.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ImageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: ImageEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entities: List<ImageEntity>)

    @Query("SELECT * FROM images_table")
    fun getAll(): Flow<List<ImageEntity>>

    @Query("SELECT * FROM images_table WHERE id = :id")
    fun getById(id: Int): Flow<ImageEntity>
}