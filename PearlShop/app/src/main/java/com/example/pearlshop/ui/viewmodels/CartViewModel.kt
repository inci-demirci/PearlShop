package com.example.pearlshop.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pearlshop.data.entity.CartProducts
import com.example.pearlshop.data.entity.Products
import com.example.pearlshop.data.repos.ProductRepository
import com.example.pearlshop.di.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val repo: ProductRepository) : ViewModel() {

    var cartItems by mutableStateOf<List<CartProducts>>(emptyList())
        private set

    fun loadCart() {
        viewModelScope.launch {
            try {
                val response = repo.getCart(Constants.USERNAME)
                if (response.success == 1) {
                    cartItems = response.urunler_sepeti ?: emptyList()
                }
            } catch (e: Exception) {
                Log.e("CartViewModel", "Sepet yüklenemedi: ${e.localizedMessage}")
            }
        }
    }

    fun addToCart(product: Products, adet: Int) {
        viewModelScope.launch {
            try {
                repo.addToCart(product, adet, Constants.USERNAME)
                loadCart()
            } catch (e: Exception) {
                Log.e("ProductViewModel", "Sepete ekleme hatası: ${e.message}")
            }
        }
    }

    fun removeFromCart(sepetId: Int) {
        viewModelScope.launch {
            try {
                val response = repo.deleteFromCart(sepetId, Constants.USERNAME)

                if (response.success == 1) {
                    val updatedList = cartItems.toMutableList()
                    updatedList.removeAll { it.sepetId == sepetId }
                    cartItems = updatedList.toList()

                } else {
                    Log.e("SepetSilme", "API'den silme başarısız oldu.")
                }
            } catch (e: Exception) {
                Log.e("SepetSilme", "Silme sırasında hata: ${e.localizedMessage}")
            }
        }
    }

    fun calculateTotalPrice(): Int {
        return cartItems.sumOf { it.fiyat * it.siparisAdeti }
    }
}