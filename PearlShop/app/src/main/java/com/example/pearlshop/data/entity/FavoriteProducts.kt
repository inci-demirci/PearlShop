package com.example.pearlshop.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteProducts(@PrimaryKey val id: Int,
                            val ad: String,
                            val marka: String,
                            val kategori: String,
                            val fiyat: Int,
                            val resim: String) {
}