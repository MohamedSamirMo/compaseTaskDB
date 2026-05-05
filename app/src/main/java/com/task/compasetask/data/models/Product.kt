package com.task.compasetask.data.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val imageVector: ImageVector = Icons.Default.AccountCircle, // default fallback
    val description: String = "",
    val category: String = "" // optional: "donut", "hot drink", etc.
)

val sampleProducts = listOf(
    // Featured Donuts (as shown in home screen)
    Product(1, "Pumpkin Spice", 4.00, Icons.Default.AccountCircle, "Seasonal pumpkin spice donut", "donut"),
    Product(2, "Strawberry Lemon", 3.75, Icons.Default.AccountCircle, "Strawberry lemonade flavored donut", "donut"),

    // Additional donuts (if needed elsewhere)
    Product(3, "Glazed Donut", 2.99, Icons.Default.AccountCircle, "Classic glazed donut", "donut"),
    Product(4, "Chocolate Sprinkle", 3.49, Icons.Default.AccountCircle, "With rainbow sprinkles", "donut"),
    Product(5, "Strawberry Frosted", 3.29, Icons.Default.AccountCircle, "Strawberry icing", "donut"),
    Product(6, "Boston Cream", 3.99, Icons.Default.AccountCircle, "Filled with vanilla cream", "donut"),

    // Hot Drinks
    Product(7, "Original Coffee", 2.50, Icons.Default.AccountCircle, "Classic brewed coffee", "hot drink"),
    Product(8, "Tea", 2.00, Icons.Default.AccountCircle, "Hot tea", "hot drink"),
    Product(9, "Espresso", 2.75, Icons.Default.AccountCircle, "Rich espresso shot", "hot drink"),

    // Iced Drinks (for completeness)
    Product(10, "Iced Caramel Latte", 4.50, Icons.Default.AccountCircle, "Chilled latte with caramel", "iced drink"),
    Product(11, "Iced Mocha", 4.75, Icons.Default.AccountCircle, "Chocolate and coffee", "iced drink")
)

// Helper to get products by category
fun getProductsByCategory(category: String): List<Product> = sampleProducts.filter { it.category == category }

// Featured products (first two)
val featuredProducts = sampleProducts.take(2)

// Hot drinks
val hotDrinks = getProductsByCategory("hot drink")