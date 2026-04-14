package com.example.swiggy.ui.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.swiggy.R // Isse aapki images access hongi
import com.example.swiggy.data.model.*

class SwiggyViewModel : ViewModel() {
    val userName = "Abhinav Kumar"
    var searchQuery = mutableStateOf("")

    private val _cartItems = mutableStateListOf<CartItem>()
    val cartItems: List<CartItem> = _cartItems

    private val _orderHistory = mutableStateListOf<List<CartItem>>()
    val orderHistory: List<List<CartItem>> = _orderHistory

    val allRestaurants = listOf(
        Restaurant(1, "Pizza Palace", "Italian • Pizza", 4.3f, "25 mins", "Free", R.drawable.pizza),
        Restaurant(2, "Biryani Hub", "North Indian • Biryani", 4.5f, "35 mins", "₹30", R.drawable.biryani),
        Restaurant(3, "Burger Barn", "American • Burgers", 4.1f, "20 mins", "Free", R.drawable.burger),
        Restaurant(4, "Dosa Corner", "South Indian", 4.6f, "15 mins", "₹20", R.drawable.dosa),
        Restaurant(5, "Chinese Wok", "Chinese • Noodles", 4.0f, "30 mins", "₹40", R.drawable.noodles),
        Restaurant(6, "Sushi Samurai", "Japanese • Sushi", 4.7f, "45 mins", "Free", R.drawable.sushi),
        Restaurant(7, "Sweet Truth", "Desserts • Ice Cream", 4.4f, "15 mins", "Free", R.drawable.ice_cream),
        Restaurant(8, "Kebab King", "Mughlai • Tandoor", 4.2f, "40 mins", "₹50", R.drawable.kebab),
        Restaurant(9, "Subway Station", "Healthy • Salads", 3.9f, "20 mins", "₹25", R.drawable.salad),
        Restaurant(10, "Pasta Primavera", "Italian • Pasta", 4.3f, "25 mins", "Free", R.drawable.pasta),
        Restaurant(11, "Chai Sutta Bar", "Beverages • Snacks", 4.5f, "10 mins", "Free", R.drawable.snacks)
    )

    fun addToCart(restaurant: Restaurant) {
        val food = FoodItem(restaurant.id, restaurant.name, "Signature Dish", 499, restaurant.imageUrl, true, restaurant.id)
        val existing = _cartItems.find { it.foodItem.id == food.id }
        if (existing != null) {
            val index = _cartItems.indexOf(existing)
            _cartItems[index] = existing.copy(quantity = existing.quantity + 1)
        } else {
            _cartItems.add(CartItem(food, 1))
        }
    }

    fun removeFromCart(cartItem: CartItem) {
        _cartItems.remove(cartItem)
    }

    fun completePayment() {
        if (_cartItems.isNotEmpty()) {
            _orderHistory.add(_cartItems.toList())
            _cartItems.clear()
        }
    }

    fun getTotalPrice(): Int = _cartItems.sumOf { it.foodItem.price * it.quantity }
}