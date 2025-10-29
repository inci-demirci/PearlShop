package com.example.pearlshop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pearlshop.ui.navigation.BottomNavigationBar
import com.example.pearlshop.ui.navigation.Screen
import com.example.pearlshop.ui.screens.CartScreen
import com.example.pearlshop.ui.screens.DetailScreen
import com.example.pearlshop.ui.screens.FavoriteScreen
import com.example.pearlshop.ui.screens.ProductList
import com.example.pearlshop.ui.theme.PearlShopTheme
import com.example.pearlshop.ui.viewmodels.CartViewModel
import com.example.pearlshop.ui.viewmodels.FavoriteViewModel
import com.example.pearlshop.ui.viewmodels.ProductViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, true)

        setContent {
            val systemUiController = rememberSystemUiController()
            SideEffect {
                systemUiController.setStatusBarColor(
                    color=Color.Transparent,
                    darkIcons = true
                )
            }
            setContent {
                PearlShopTheme {
                    val navController = rememberNavController()
                    val productViewModel: ProductViewModel = hiltViewModel()
                    val cartViewModel: CartViewModel = hiltViewModel()
                    val favoriteViewModel: FavoriteViewModel = hiltViewModel()


                    Scaffold(
                        bottomBar = {
                            val currentRoute =
                                navController.currentBackStackEntryAsState().value?.destination?.route
                            if (currentRoute != null && !currentRoute.startsWith("detail")) {
                                BottomNavigationBar(navController)
                            }
                        }
                    ) { innerPadding ->
                        NavHost(
                            navController = navController,
                            startDestination = Screen.ProductList.route,
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            composable(Screen.ProductList.route) {
                                ProductList(productViewModel, favoriteViewModel, navController)
                            }
                            composable(
                                Screen.DetailScreen.route,
                                arguments = listOf(navArgument("productId") {
                                    type = NavType.IntType
                                })
                            ) { backStackEntry ->
                                val productId = backStackEntry.arguments?.getInt("productId") ?: 0
                                DetailScreen(productId, productViewModel, cartViewModel,favoriteViewModel ,navController)
                            }
                            composable(Screen.CartScreen.route) {
                                CartScreen(cartViewModel)
                            }
                            composable(Screen.FavoriteScreen.route) {
                                FavoriteScreen(favoriteViewModel, navController)
                            }
                        }
                    }
                }
            }
        }
    }
}