package com.example.pearlshop.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pearlshop.data.entity.FavoriteProducts
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: FavoriteProducts)

    @Delete
    suspend fun deleteFavorite(favorite: FavoriteProducts)

    @Query("SELECT * FROM favorites")
    fun getAllFavorites(): Flow<List<FavoriteProducts>>
}