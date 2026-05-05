package com.task.compasetask.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import com.task.compasetask.data.models.*
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
    var selectedCategory by remember { mutableStateOf("Hot Drinks") }
    var selectedLocation by remember { mutableStateOf("Cairo") }
    val locations = listOf("Cairo", "Alexandria", "Giza", "Mansoura")
    var locationMenuExpanded by remember { mutableStateOf(false) }

    val categories = listOf(
        "Hot Drinks" to "hot drink",
        "Iced Drinks" to "iced drink",
        "Donuts" to "donut"
    )

    fun productsForCategory(categoryTitle: String): List<Product> {
        return when (categoryTitle) {
            "Hot Drinks" -> getProductsByCategory("hot drink")
            "Iced Drinks" -> getProductsByCategory("iced drink")
            "Donuts" -> getProductsByCategory("donut")
            else -> emptyList()
        }
    }

    Scaffold(
        containerColor = Color(0xFFFFF5E1),
        bottomBar = {
            // ==================== Bottom Navigation Bar ====================
            NavigationBar(
                containerColor = Color(0xFFFFF5E1),
                tonalElevation = 0.dp
            ) {
                NavigationBarItem(
                    selected = true, // افتراضياً الصفحة الحالية هي Home
                    onClick = { /* بالفعل في Home */ },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate(Screen.Menu.route) }, // تأكد من وجود هذا المسار
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
                    onClick = { navController.navigate(Screen.Profile.route) }, // أنشئ Screen.Profile إذا لزم
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
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            // ==================== Top Bar ====================
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Location Selector with Dropdown
                Box {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color(0xFFF5E6D3))
                            .padding(horizontal = 14.dp, vertical = 8.dp)
                            .clickable { locationMenuExpanded = true }
                    ) {
                        Text(
                            text = selectedLocation,
                            color = Color(0xFF795548),
                            fontSize = 14.sp
                        )
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Change location",
                            tint = Color(0xFF795548),
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    DropdownMenu(
                        expanded = locationMenuExpanded,
                        onDismissRequest = { locationMenuExpanded = false }
                    ) {
                        locations.forEach { location ->
                            DropdownMenuItem(
                                text = { Text(location) },
                                onClick = {
                                    selectedLocation = location
                                    locationMenuExpanded = false
                                }
                            )
                        }
                    }
                }

                // Cart Icon
                BadgedBox(
                    badge = {
                        if (cartItemCount > 0) {
                            Badge(
                                containerColor = Color(0xFF795548),
                                contentColor = Color.White
                            ) {
                                Text(cartItemCount.toString(), fontSize = 12.sp)
                            }
                        }
                    }
                ) {
                    IconButton(onClick = { navController.navigate(Screen.Cart.route) }) {
                        Icon(
                            Icons.Default.ShoppingCart,
                            contentDescription = "Cart",
                            tint = Color(0xFF795548)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ==================== NEW Banner ====================
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFE0B2))
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "NEW",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF006D5B),
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Pumpkin Spice donut!",
                        color = Color(0xFF4A3728),
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ==================== Featured ====================
            Text(
                text = "Featured",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4A3728)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                featuredProducts.forEach { product ->
                    ProductCard(
                        product = product,
                        onAddToCart = { cartViewModel.addToCart(product) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // ==================== Categories ====================
            Text(
                text = "Categories",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF4A3728)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                categories.forEach { (categoryTitle, _) ->
                    val isSelected = categoryTitle == selectedCategory
                    AssistChip(
                        onClick = { selectedCategory = categoryTitle },
                        label = {
                            Text(
                                categoryTitle,
                                color = if (isSelected) Color.White else Color(0xFF4A3728)
                            )
                        },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = if (isSelected) Color(0xFFFF8C42) else Color(0xFFF5E6D3),
                            labelColor = if (isSelected) Color.White else Color(0xFF4A3728)
                        ),
                        shape = RoundedCornerShape(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // ==================== Dynamic Section ====================
            val currentProducts = productsForCategory(selectedCategory)
            if (currentProducts.isNotEmpty()) {
                Text(
                    text = selectedCategory,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF4A3728)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    currentProducts.forEach { product ->
                        ProductCard(
                            product = product,
                            onAddToCart = { cartViewModel.addToCart(product) }
                        )
                    }
                }
            } else {
                Text(
                    text = "No products in this category",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp)) // مسافة صغيرة قبل نهاية المحتوى
        }
    }
}