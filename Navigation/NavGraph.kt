package com.example.swiggy.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.swiggy.ui.viewmodel.SwiggyViewModel
import com.example.swiggy.ui.screens.*

@Composable
fun NavGraph(navController: NavHostController) {
    val swiggyViewModel: SwiggyViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(navController, swiggyViewModel)
        }

        composable(
            route = "restaurant/{restaurantId}",
            arguments = listOf(navArgument("restaurantId") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("restaurantId") ?: 0
            RestaurantScreen(id, navController, swiggyViewModel)
        }

        composable("cart") {
            CartScreen(navController, swiggyViewModel)
        }

        composable("profile") {
            ProfileScreen(navController, swiggyViewModel)
        }
    }
}