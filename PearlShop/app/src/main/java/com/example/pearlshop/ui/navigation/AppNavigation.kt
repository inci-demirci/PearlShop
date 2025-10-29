package com.example.pearlshop.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.TopAppBarColors
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object ProductList : Screen("product_list","Ana Sayfa", Icons.Default.Home)
    object DetailScreen : Screen("detail/{productId}","Ürün Detayı", Icons.Default.Info)
    object CartScreen : Screen("cart","Sepetim", Icons.Default.ShoppingCart)
    object FavoriteScreen : Screen("favorite","Favorilerim", Icons.Default.Favorite)
}
