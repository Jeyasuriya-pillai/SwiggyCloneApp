package com.example.swiggy.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import com.example.swiggy.ui.viewmodel.SwiggyViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(navController: NavController, viewModel: SwiggyViewModel) {
    val cartItems = viewModel.cartItems
    val total = viewModel.getTotalPrice()
    var showReceipt by remember { mutableStateOf(false) }

    if (showReceipt) {
        AlertDialog(
            onDismissRequest = { showReceipt = false },
            confirmButton = {
                Button(onClick = {
                    viewModel.completePayment()
                    showReceipt = false
                    navController.navigate("home")
                }) { Text("Confirm") }
            },
            title = { Text("Order Placed! ✅") },
            text = { Text("Your food will be delivered in 35 mins. Total: ₹$total") }
        )
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("My Cart") }, navigationIcon = { IconButton(onClick = { navController.popBackStack() }) { Icon(Icons.Default.ArrowBack, null) } }) },
        bottomBar = {
            if (cartItems.isNotEmpty()) {
                Button(onClick = { showReceipt = true }, modifier = Modifier.fillMaxWidth().padding(16.dp).height(55.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFC8019))) {
                    Text("PROCEED TO PAY ₹$total", fontWeight = FontWeight.Bold)
                }
            }
        }
    ) { padding ->
        if (cartItems.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("Empty Cart 🛒") }
        } else {
            LazyColumn(modifier = Modifier.padding(padding).fillMaxSize()) {
                item { Text("Delivery in 35 mins", modifier = Modifier.padding(16.dp), color = Color(0xFFFC8019), fontWeight = FontWeight.Bold) }

                items(cartItems) { item ->
                    ListItem(
                        headlineContent = { Text(item.foodItem.name) },
                        supportingContent = { Text("Qty: ${item.quantity} • ₹${item.foodItem.price}") },
                        trailingContent = {
                            IconButton(onClick = { viewModel.removeFromCart(item) }) {
                                Icon(Icons.Default.Delete, contentDescription = null, tint = Color.Red)
                            }
                        }
                    )
                }
            }
        }
    }
}