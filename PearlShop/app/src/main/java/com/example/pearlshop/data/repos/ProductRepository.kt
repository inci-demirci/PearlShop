package com.example.pearlshop.data.repos

import com.example.pearlshop.data.datasource.Datasources
import com.example.pearlshop.data.entity.Products
import javax.inject.Inject

class ProductRepository @Inject constructor(private val ds: Datasources) {
    suspend fun getProducts() = ds.getAllProducts()
    suspend fun addToCart(product: Products, adet: Int, kullaniciAdi: String) = ds.addToCart(product, adet, kullaniciAdi)
    suspend fun getCart(kullaniciAdi: String) = ds.getCart(kullaniciAdi)
    suspend fun deleteFromCart(sepetId: Int, kullaniciAdi: String) = ds.deleteFromCart(sepetId, kullaniciAdi)
}