package com.example.pearlshop.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pearlshop.data.entity.FavoriteProducts

@Database(entities = [FavoriteProducts::class], version = 1)
abstract class FavoritesDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoritesDao
}