package com.example.pearlshop.data.entity

data class CartResponse(val urunler_sepeti: List<CartProducts>? = null ,
                        val success: Int) {
}