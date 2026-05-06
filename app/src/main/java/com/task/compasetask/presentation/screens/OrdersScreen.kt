package com.task.compasetask.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.task.compasetask.presentation.OrderItemCard
import com.task.compasetask.presentation.nav.Screen
import com.task.compasetask.presentation.viewModel.CartViewModel
import com.task.compasetask.presentation.viewModel.OrdersViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrdersScreen(
    navController: NavController,
    ordersViewModel: OrdersViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel()
) {
    val orders by ordersViewModel.orders.collectAsState()
    val cartItemCount by cartViewModel.cartItemCount.collectAsState()

    Scaffold(
        containerColor = Color(0xFFFFF6F2),
        topBar = {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                TopAppBar(
                    title = {Text("My Orders"
                    , textAlign = TextAlign.Center,)},
                    navigationIcon = {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Image(
                                painter = painterResource(id = com.task.compasetask.R.drawable.ic_back),
                                contentDescription = "Back",
                                modifier = Modifier.size(34.dp)
                            )
                        }
                    }
                    ,
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFFFF5E1))
                )
            }
        },
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
                    selected = true,
                    onClick = { /* already here */ },
                    icon = { Icon(Icons.Default.Menu, contentDescription = "Menu") },
                    label = { Text("Menu") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate(Screen.Cart.route) },
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (orders.isEmpty()) {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("No orders yet", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = { navController.navigate(Screen.Home.route) },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF795548))
                    ) {
                        Text("Start Shopping", color = Color.White)
                    }
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(orders) { order ->
                        OrderItemCard(order = order)
                    }
                }
            }
        }
    }
}