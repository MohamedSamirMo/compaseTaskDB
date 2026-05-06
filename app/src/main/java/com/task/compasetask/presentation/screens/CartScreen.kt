package com.task.compasetask.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.task.compasetask.presentation.CartItemRow
import com.task.compasetask.presentation.nav.Screen
import com.task.compasetask.presentation.viewModel.CartViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    navController: NavController,
    viewModel: CartViewModel = hiltViewModel()
) {
    val cartItems by viewModel.cartItems.collectAsState()
    val totalPrice by viewModel.totalPrice.collectAsState()
    val cartItemCount by viewModel.cartItemCount.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Cart") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFFFF5E1))
            )
        },
        containerColor = Color(0xFFFFF6F2), // نفس خلفية HomeScreen
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 0.dp
            ) {
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate(Screen.Home.route) },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate(Screen.Orders.route) },
                    icon = { Icon(Icons.Default.Menu, contentDescription = "Menu") },
                    label = { Text("Menu") }
                )
                NavigationBarItem(
                    selected = true,
                    onClick = { },
                    icon = {
                        BadgedBox(
                            badge = {
                                if (cartItemCount > 0) {
                                    Badge(
                                        containerColor = Color(0xFF795548),
                                        contentColor = Color.White
                                    ) { Text(cartItemCount.toString()) }
                                }
                            }
                        ) {
                            Icon(Icons.Default.ShoppingCart, contentDescription = "Cart")
                        }
                    },
                    label = { Text("Cart") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate(Screen.Profile.route) },
                    icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
                    label = { Text("Profile") }
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp) // إضافة padding أفقي مماثل لـ HomeScreen
        ) {
            if (cartItems.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Your cart is empty",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color(0xFF174C4F) // نفس لون النص الداكن في HomeScreen
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(vertical = 16.dp), // مسافات رأسية متسقة
                    verticalArrangement = Arrangement.spacedBy(12.dp) // نفس تباعد البطاقات
                ) {
                    items(cartItems) { cartItem ->
                        CartItemRow(
                            item = cartItem,
                            onIncrease = { viewModel.updateQuantity(cartItem.product, cartItem.quantity + 1) },
                            onDecrease = { viewModel.updateQuantity(cartItem.product, cartItem.quantity - 1) },
                            onRemove = { viewModel.removeFromCart(cartItem.product) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // بطاقة إجمالي السعر (مشابهة لـ SimpleProductCard ولكن للتوتال)
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Total",
                                style = MaterialTheme.typography.titleLarge,
                                color = Color(0xFF174C4F),
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "$${String.format("%.2f", totalPrice)}",
                                style = MaterialTheme.typography.titleLarge,
                                color = Color(0xFF795548),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { viewModel.checkout() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF795548) // نفس لون الأزرار في HomeScreen
                    )
                ) {
                    Text(
                        text = "Checkout",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

    val checkoutSuccess by viewModel.checkoutSuccess.collectAsState()
    LaunchedEffect(checkoutSuccess) {
        if (checkoutSuccess) {
            navController.navigate(Screen.Orders.route)
            viewModel.resetCheckoutSuccess()
        }
    }
}