package com.example.pearlshop.data.entity

data class CartProducts(val sepetId: Int,
                        val ad: String,
                        val resim: String,
                        val kategori: String,
                        val fiyat: Int,
                        val marka: String,
                        val siparisAdeti: Int,
                        val kullaniciAdi: String) {
}