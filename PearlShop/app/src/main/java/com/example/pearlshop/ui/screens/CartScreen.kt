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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.pearlshop.R
import com.example.pearlshop.di.Constants
import com.example.pearlshop.ui.theme.regular
import com.example.pearlshop.ui.viewmodels.CartViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(viewModel: CartViewModel) {
    LaunchedEffect(Unit) { viewModel.loadCart() }

    val cartItems = viewModel.cartItems
    val toplam = viewModel.calculateTotalPrice()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.background))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Sepetim",
            fontWeight = FontWeight.ExtraBold,
            fontFamily = regular,
            fontSize = 24.sp,
            color = colorResource(R.color.main_color)
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (cartItems.isEmpty()) {
            Box(modifier = Modifier
                .fillMaxSize(),
                contentAlignment = Alignment.Center) {
                Text("Sepetiniz boş",
                    fontFamily = regular,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray)
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(cartItems) { item ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(Constants.BASE_URL + "urunler/resimler/" + item.resim),
                                contentDescription = item.ad,
                                modifier = Modifier.size(80.dp)
                            )

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(item.marka,
                                    fontFamily = regular,
                                    fontSize = 14.sp,
                                    color = Color.Black)

                                Text(item.ad,
                                    fontFamily = regular,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp)

                                Text("₺${item.fiyat} x ${item.siparisAdeti}",
                                    fontFamily = regular,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 14.sp)

                                Text("Toplam: ₺${item.fiyat * item.siparisAdeti}",
                                    fontFamily = regular,
                                    fontWeight = FontWeight.Bold)
                            }

                            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                                IconButton(onClick = { viewModel.removeFromCart(item.sepetId) }) {
                                    Icon(
                                        Icons.Default.Delete,
                                        contentDescription = "Sil",
                                        tint = Color.Black)
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Toplam: ₺$toplam",
                    fontFamily = regular,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp)

                Button(
                    onClick = { },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    ),
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
                                shape = RoundedCornerShape(12.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "SEPETİ ONAYLA",
                            fontFamily = regular,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}
