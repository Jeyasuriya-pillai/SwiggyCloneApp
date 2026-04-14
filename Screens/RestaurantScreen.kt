package com.example.swiggy.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.swiggy.R
import com.example.swiggy.data.model.FoodItem
import com.example.swiggy.data.model.Restaurant
import com.example.swiggy.ui.components.FoodCard
import com.example.swiggy.ui.viewmodel.SwiggyViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantScreen(restaurantId: Int, navController: NavController, viewModel: SwiggyViewModel) {
    val foodItems = listOf(
        FoodItem(1, "Margherita Pizza", "Classic tomato sauce and mozzarella", 299, R.drawable.margherita_pizza, true, restaurantId),
        FoodItem(2, "Pepperoni Pizza", "Loaded with pepperoni slices", 349, R.drawable.pepperoni_pizza, false, restaurantId),
        FoodItem(3, "Garlic Bread", "Toasted with garlic butter and herbs", 129, R.drawable.garlic_bread, true, restaurantId),
        FoodItem(4, "Pasta Arrabiata", "Spicy tomato pasta", 249, R.drawable.pasta_arrabiata, true, restaurantId),
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Restaurant Menu", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            NavigationBar(containerColor = Color.White, tonalElevation = 8.dp) {
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("home") },
                    icon = { Icon(Icons.Default.Home, null) },
                    label = { Text("Home") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("cart") },
                    icon = {
                        BadgedBox(badge = {
                            if(viewModel.cartItems.isNotEmpty()) {
                                Badge { Text(viewModel.cartItems.size.toString()) }
                            }
                        }) { Icon(Icons.Default.ShoppingCart, null) }
                    },
                    label = { Text("Cart") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("profile") },
                    icon = { Icon(Icons.Default.Person, null) },
                    label = { Text("Account") }
                )
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8F8F8))
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    "Popular Dishes",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
            items(foodItems) { item ->
                FoodCard(
                    foodItem = item,
                    onAddToCart = {
                        // WORKING CART LOGIC
                        viewModel.addToCart(
                            Restaurant(item.id, item.name, "", 0f, "", "", item.imageUrl)
                        )
                    }
                )
            }
            item { Spacer(modifier = Modifier.height(20.dp)) }
        }
    }
}