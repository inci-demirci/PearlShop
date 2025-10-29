package com.example.pearlshop.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pearlshop.data.entity.Products
import com.example.pearlshop.data.repos.ProductRepository
import com.example.pearlshop.di.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repo: ProductRepository
) : ViewModel() {

    var products by mutableStateOf<List<Products>>(emptyList())

    var favorites = mutableStateListOf<Products>()

    var searchQuery by mutableStateOf("")

    fun loadProducts() {
        viewModelScope.launch {
            try {
                val response = repo.getProducts()
                if (response.success == 1) {
                    products = response.urunler
                }
            } catch (e: Exception) {
                Log.e("ProductViewModel", "Ürünler yüklenemedi: ${e.message}")
            }
        }
    }

    fun toggleFavorite(product: Products) {
        if (favorites.contains(product)) {
            favorites.remove(product)
        } else {
            favorites.add(product)
        }
    }

    fun updateSearchQuery(query: String) {
        searchQuery = query
    }

    fun filteredProducts(): List<Products> {
        return if (searchQuery.isBlank()) {
            products
        } else {
            products.filter {
                it.ad.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    fun addToCart(product: Products, adet: Int) {
        viewModelScope.launch {
            try {
                repo.addToCart(product, adet, Constants.USERNAME)
            } catch (e: Exception) {

            }
        }
    }
}