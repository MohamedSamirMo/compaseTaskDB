package com.task.compasetask.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
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

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Cart") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
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
        ) {
            if (cartItems.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Your cart is empty", style = MaterialTheme.typography.titleMedium)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
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
                Divider(thickness = 1.dp)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Total:", style = MaterialTheme.typography.titleLarge)
                    Text("$${String.format("%.2f", totalPrice)}", style = MaterialTheme.typography.titleLarge)
                }
                Button(
                    onClick = { viewModel.checkout() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF795548))
                ) {
                    Text("Checkout", color = Color.White)
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

    // مراقبة نجاح إنشاء الطلب للانتقال إلى شاشة الطلبات
    val checkoutSuccess by viewModel.checkoutSuccess.collectAsState()
    LaunchedEffect(checkoutSuccess) {
        if (checkoutSuccess) {
            navController.navigate(Screen.Orders.route)
            viewModel.resetCheckoutSuccess()
        }
    }
}