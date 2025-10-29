package com.example.pearlshop.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.pearlshop.R
import com.example.pearlshop.data.entity.FavoriteProducts
import com.example.pearlshop.di.Constants
import com.example.pearlshop.ui.theme.regular
import com.example.pearlshop.ui.viewmodels.CartViewModel
import com.example.pearlshop.ui.viewmodels.FavoriteViewModel
import com.example.pearlshop.ui.viewmodels.ProductViewModel

@Composable
fun DetailScreen(
    productId: Int,
    viewModel: ProductViewModel,
    cartViewModel: CartViewModel,
    favoriteViewModel: FavoriteViewModel,
    navController: NavController
) {
    val product = viewModel.products.find { it.id == productId } ?: return
    var adet by remember { mutableStateOf(1) }
    val favorites by favoriteViewModel.favorites.observeAsState(emptyList())


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.background))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack,
                    contentDescription = "Geri",
                    tint = Color.Black)
            }
            Text(
                text = "Ürün Detayı",
                fontSize = 24.sp,
                fontFamily = regular,
                fontWeight = FontWeight.Black,
                color = colorResource(R.color.main_color)
            )
            val isFavorite = favorites.any { it.id == product.id }
            IconButton(
                onClick = { val favorite = FavoriteProducts(
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
                    } }
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Favori",
                    tint = if (isFavorite) Color.Red else Color.Black
                )
            }
        }

        Image(
            painter = rememberAsyncImagePainter(Constants.BASE_URL + "urunler/resimler/" + product.resim),
            contentDescription = product.ad,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .padding(horizontal = 24.dp)
                .clip(RoundedCornerShape(20.dp))
        )
        Spacer(modifier = Modifier.height(24.dp))

        Card(
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .padding(horizontal = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = product.ad,
                    fontFamily = regular,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = Color.Black
                )
                Text(
                    text = "₺${product.fiyat}",
                    fontFamily = regular,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Marka: ${product.marka}",
                    fontFamily = regular ,
                    color = Color.Black)
                Text(
                    text = "Kategori: ${product.kategori}",
                    fontFamily = regular,
                    color = Color.Black)
            }
        }
        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,

        ) {
            IconButton(
                onClick = { if (adet > 1) adet-- },
                modifier = Modifier
                    .size(40.dp)
                    .background(colorResource(R.color.main_color),
                        shape = CircleShape)
            ) {
                Icon(
                    Icons.Default.Remove,
                    contentDescription = "Azalt",
                    tint = Color.White)
            }

            Text(
                adet.toString(),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            IconButton(
                onClick = { adet++ },
                modifier = Modifier
                    .size(40.dp)
                    .background(colorResource(R.color.main_color),
                        shape = CircleShape)
            ) {
                Icon(Icons.Default.Add,
                    contentDescription = "Arttır",
                    tint = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                cartViewModel.addToCart(product, adet)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .padding(horizontal = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            contentPadding = PaddingValues()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFF6B5894),
                                Color(0xFF7A65A6),
                                Color(0xFF8C76B8)
                            )
                        ),
                        shape = RoundedCornerShape(30.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "SEPETE EKLE",
                    fontFamily = regular,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

