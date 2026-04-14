package com.example.swiggy.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.swiggy.ui.viewmodel.SwiggyViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: SwiggyViewModel) {
    val searchText by viewModel.searchQuery
    val filtered = viewModel.allRestaurants.filter { it.name.contains(searchText, true) }

    val categories = listOf("Offers", "Pizza", "Biryani", "Burger", "Thali", "Cakes")

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Delivery to", fontSize = 12.sp, color = Color.Gray)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("Mumbai, Maharashtra", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                            Icon(Icons.Default.KeyboardArrowDown, null, modifier = Modifier.size(20.dp), tint = Color(0xFFFC8019))
                        }
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate("profile") }) {
                        Icon(Icons.Default.AccountCircle, contentDescription = null, modifier = Modifier.size(32.dp))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            NavigationBar(containerColor = Color.White, tonalElevation = 10.dp) {
                NavigationBarItem(
                    selected = true,
                    onClick = { },
                    icon = { Icon(Icons.Default.Home, null) },
                    label = { Text("Home") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("cart") },
                    icon = {
                        BadgedBox(badge = {
                            if(viewModel.cartItems.isNotEmpty()) Badge { Text(viewModel.cartItems.size.toString()) }
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
                .padding(padding)
                .fillMaxSize()
                .background(Color(0xFFF8F9FA))
        ) {
            item {
                OutlinedTextField(
                    value = searchText,
                    onValueChange = { viewModel.searchQuery.value = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    placeholder = { Text("Search for dishes, restaurants...", fontSize = 14.sp) },
                    leadingIcon = { Icon(Icons.Default.Search, null, tint = Color(0xFFFC8019)) },
                    trailingIcon = { Icon(Icons.Default.Mic, null, tint = Color.Gray) },
                    shape = RoundedCornerShape(16.dp),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFFC8019),
                        unfocusedBorderColor = Color.LightGray,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )
            }

            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    items(categories) { cat ->
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            border = BorderStroke(1.dp, Color.LightGray),
                            color = Color.White
                        ) {
                            Text(cat, modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp), fontWeight = FontWeight.Medium)
                        }
                    }
                }
            }

            item {
                Text("All Restaurants", fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier.padding(16.dp))
            }

            items(filtered) { rest ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .clickable { navController.navigate("restaurant/${rest.id}") },
                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.cardElevation(4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column {
                        Box(modifier = Modifier.height(180.dp).fillMaxWidth()) {
                            AsyncImage(
                                model = rest.imageUrl,
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                            Surface(
                                color = Color(0xFF256FEF),
                                shape = RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp),
                                modifier = Modifier.padding(top = 16.dp)
                            ) {
                                Text("60% OFF up to ₹120", color = Color.White, fontSize = 10.sp, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp), fontWeight = FontWeight.Bold)
                            }
                        }

                        Column(Modifier.padding(16.dp)) {
                            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(rest.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                                Surface(color = Color(0xFF48C479), shape = RoundedCornerShape(6.dp)) {
                                    Row(Modifier.padding(horizontal = 6.dp, vertical = 2.dp), verticalAlignment = Alignment.CenterVertically) {
                                        Text("${rest.rating}", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                        Icon(Icons.Default.Star, null, tint = Color.White, modifier = Modifier.size(12.dp))
                                    }
                                }
                            }
                            Text(rest.cuisine, color = Color.Gray, fontSize = 14.sp)

                            HorizontalDivider(Modifier.padding(vertical = 12.dp), thickness = 0.5.dp)

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Timer, null, modifier = Modifier.size(16.dp), tint = Color.Gray)
                                Text(" ${rest.deliveryTime} • ₹150 for two", fontSize = 12.sp, color = Color.Gray)
                                Spacer(Modifier.weight(1f))
                                Button(
                                    onClick = { viewModel.addToCart(rest) },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFC8019)),
                                    contentPadding = PaddingValues(horizontal = 20.dp),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text("ADD", fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}
