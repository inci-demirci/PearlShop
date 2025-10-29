package com.example.pearlshop.data.repos

import com.example.pearlshop.data.entity.FavoriteProducts
import com.example.pearlshop.data.local.FavoritesDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteRepository @Inject constructor(private val dao: FavoritesDao) {
    val favorites: Flow<List<FavoriteProducts>> = dao.getAllFavorites()

    suspend fun addFavorite(item: FavoriteProducts) = dao.insertFavorite(item)
    suspend fun removeFavorite(item: FavoriteProducts) = dao.deleteFavorite(item)
}