package com.example.pearlshop.data.datasource

import com.example.pearlshop.data.entity.Products
import com.example.pearlshop.retrofit.ApiService

class Datasources(private val api: ApiService) {
    suspend fun getAllProducts() = api.getAllProducts()

    suspend fun addToCart(product: Products, adet: Int, kullaniciAdi: String) =
        api.addToCart(
            product.ad,
            product.resim,
            product.kategori,
            product.fiyat,
            product.marka,
            adet,
            kullaniciAdi
        )

    suspend fun getCart(kullaniciAdi: String) = api.getCart(kullaniciAdi)

    suspend fun deleteFromCart(sepetId: Int, kullaniciAdi: String) = api.deleteFromCart(sepetId, kullaniciAdi)
}