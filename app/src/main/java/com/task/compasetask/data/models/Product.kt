package com.task.compasetask.data.models


import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle

data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val imageVector: ImageVector,
    val description: String = ""
)

val sampleProducts = listOf(
    Product(1, "Glazed Donut", 2.99, Icons.Filled.AccountCircle, "Classic glazed donut"),
    Product(2, "Chocolate Sprinkle", 3.49, Icons.Filled.AccountCircle, "With rainbow sprinkles"),
    Product(3, "Strawberry Frosted", 3.29, Icons.Filled.AccountCircle, "Strawberry icing"),
    Product(4, "Boston Cream", 3.99, Icons.Filled.AccountCircle, "Filled with vanilla cream"),
)