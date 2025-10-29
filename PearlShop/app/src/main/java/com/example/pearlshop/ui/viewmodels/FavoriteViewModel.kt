package com.example.pearlshop.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.pearlshop.data.entity.FavoriteProducts
import com.example.pearlshop.data.repos.FavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val repository: FavoriteRepository) : ViewModel() {
    val favorites = repository.favorites.asLiveData()

    fun add(products: FavoriteProducts) = viewModelScope.launch {
        repository.addFavorite(products)
    }

    fun remove(products: FavoriteProducts) = viewModelScope.launch {
        repository.removeFavorite(products)
    }
}
