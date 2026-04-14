package com.example.swiggy.data.model

data class Restaurant(
    val id: Int,
    val name: String,
    val cuisine: String,
    val rating: Float,
    val deliveryTime: String,
    val deliveryFee: String,
    val imageUrl: Int,
    val isVeg: Boolean = false,
    val discount: String = ""
)

data class FoodItem(
    val id: Int,
    val name: String,
    val description: String,
    val price: Int,
    val imageUrl: Int,
    val isVeg: Boolean,
    val restaurantId: Int
)

data class CartItem(
    val foodItem: FoodItem,
    var quantity: Int
)