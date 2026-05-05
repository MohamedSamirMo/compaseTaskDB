package com.task.compasetask.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.task.compasetask.presentation.nav.Screen
import com.task.compasetask.presentation.viewModel.CartViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(
    navController: NavController,
    cartViewModel: CartViewModel = hiltViewModel()
) {
    val cartItemCount by cartViewModel.cartItemCount.collectAsState()

    Scaffold(
        containerColor = Color(0xFFFFF5E1),
        bottomBar = {
            // ==================== Bottom Navigation Bar (مطابق للـ HomeScreen) ====================
            NavigationBar(
                containerColor = Color(0xFFFFF5E1),
                tonalElevation = 0.dp
            ) {
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate(Screen.Home.route) },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") }
                )
                NavigationBarItem(
                    selected = true, // لأننا في شاشة Menu
                    onClick = { /* بالفعل في Menu */ },
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            // محتوى بسيط للقائمة (يمكنك تطويره لاحقاً)
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFE0B2))
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "🍽️ Menu",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF4A3728)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "All our delicious items will appear here soon!",
                        color = Color(0xFF795548),
                        fontSize = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // يمكنك إضافة قائمة حقيقية لاحقاً
            Text(
                text = "Categories",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF4A3728)
            )
            Spacer(modifier = Modifier.height(12.dp))
            // مثال: عرض بعض الأزرار أو المنتجات
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                AssistChip(
                    onClick = { /* TODO */ },
                    label = { Text("Hot Drinks") },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = Color(0xFFF5E6D3),
                        labelColor = Color(0xFF4A3728)
                    )
                )
                AssistChip(
                    onClick = { /* TODO */ },
                    label = { Text("Iced Drinks") },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = Color(0xFFF5E6D3),
                        labelColor = Color(0xFF4A3728)
                    )
                )
                AssistChip(
                    onClick = { /* TODO */ },
                    label = { Text("Donuts") },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = Color(0xFFF5E6D3),
                        labelColor = Color(0xFF4A3728)
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}