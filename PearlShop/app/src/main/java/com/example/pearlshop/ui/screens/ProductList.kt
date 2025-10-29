package com.example.pearlshop.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.pearlshop.R
import com.example.pearlshop.data.entity.FavoriteProducts
import com.example.pearlshop.di.Constants
import com.example.pearlshop.ui.theme.regular
import com.example.pearlshop.ui.viewmodels.FavoriteViewModel
import com.example.pearlshop.ui.viewmodels.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductList(viewModel: ProductViewModel,favoriteViewModel: FavoriteViewModel, navController: NavController) {
    LaunchedEffect(Unit) { viewModel.loadProducts() }

    val favorites by favoriteViewModel.favorites.observeAsState(emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.background))
            .padding(8.dp)
    ) {
        OutlinedTextField(
            value = viewModel.searchQuery,
            onValueChange = {
                viewModel.updateSearchQuery(it)
                viewModel.loadProducts()
            },
            label = { Text(text = "Search") },
            leadingIcon = {
                Icon(
                    Icons.Filled.Search,
                    contentDescription = "Search",
                    tint = Color.Black
                )
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(32.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                focusedLabelColor = Color.Black,
                cursorColor = Color.Black,
                focusedBorderColor = colorResource(R.color.main_color),
                unfocusedBorderColor = Color.Black
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(viewModel.filteredProducts()) { product ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .padding(vertical = 4.dp)
                        .clickable { navController.navigate("detail/${product.id}") },
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {

                        Column(
                            modifier = Modifier
                                .align(Alignment.TopCenter)
                                .padding(top = 16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(Constants.BASE_URL + "urunler/resimler/" + product.resim),
                                contentDescription = product.ad,
                                modifier = Modifier.size(100.dp)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(product.marka, fontFamily = regular)
                            Text(product.ad, fontFamily = regular, fontWeight = FontWeight.Bold)
                        }

                        val isFavorite = favorites.any { it.id == product.id }

                        IconButton(
                            onClick = {
                                val favorite = FavoriteProducts(
                                    id = product.id,
                                    ad = product.ad,
                                    marka = product.marka,
                                    kategori = product.kategori,
                                    fiyat = product.fiyat,
                                    resim = product.resim
                                )
                                if (isFavorite) {
                                    favoriteViewModel.remove(favorite)
                                } else {
                                    favoriteViewModel.add(favorite)
                                }
                            },
                            modifier = Modifier.align(Alignment.TopEnd)
                        ) {
                            Icon(
                                imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = "Favori",
                                tint = if (isFavorite) Color.Red else Color.Black
                            )
                        }

                        Row(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "₺${product.fiyat}",
                                fontFamily = regular,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )

                            IconButton(onClick = { viewModel.addToCart(product, 1) }) {
                                Icon(
                                    imageVector = Icons.Default.ShoppingCart,
                                    contentDescription = "Sepete Ekle",
                                    tint = colorResource(R.color.main_color)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}