package com.task.compasetask.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.task.compasetask.data.models.sampleProducts
import com.task.compasetask.presentation.ProductCard
import com.task.compasetask.presentation.nav.Screen
import com.task.compasetask.presentation.viewModel.CartViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    cartViewModel: CartViewModel = hiltViewModel()
) {
    val cartItemCount by cartViewModel.cartItemCount.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Glazed Donuts", color = Color(0xFF795548)) },
                actions = {
                    IconButton(onClick = { navController.navigate(Screen.Cart.route) }) {
                        Badge(
                            containerColor = Color(0xFF795548),
                            contentColor = Color.White
                        ) {
                            Text(cartItemCount.toString())
                        }
                        Icon(Icons.Default.ShoppingCart, contentDescription = "Cart")
                    }
                    IconButton(onClick = { navController.navigate(Screen.Orders.route) }) {
                        Icon(Icons.Default.List, contentDescription = "Orders")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFFFF5E1))
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Our Donuts 🍩",
                style = MaterialTheme.typography.titleLarge,
                color = Color(0xFF795548)
            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(sampleProducts) { product ->
                    ProductCard(
                        product = product,
                        onAddToCart = { cartViewModel.addToCart(product) }
                    )
                }
            }
        }
    }
}