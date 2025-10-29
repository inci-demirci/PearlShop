package com.example.pearlshop.ui.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.pearlshop.R

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        Screen.ProductList,
        Screen.CartScreen,
        Screen.FavoriteScreen
    )
    NavigationBar(containerColor = Color.White) {
        val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route
        items.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(screen.icon, contentDescription = screen.title) },
                label = { Text(screen.title) },
                selected = currentDestination?.startsWith(screen.route) == true,
                onClick = {
                    if (currentDestination != screen.route) {
                        navController.navigate(screen.route) {
                            popUpTo(Screen.ProductList.route) { inclusive = false }
                            launchSingleTop = true
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = colorResource(R.color.main_color),
                    unselectedIconColor = Color.DarkGray,
                    selectedTextColor = colorResource(R.color.main_color),
                    unselectedTextColor = Color.DarkGray,
                    indicatorColor = Color.White
                )
            )
        }
    }
}
