package com.task.compasetask.data.models

import java.util.UUID

data class Order(
    val id: String = UUID.randomUUID().toString(),
    val items: List<CartItem>,
    val totalPrice: Double,
    val timestamp: Long = System.currentTimeMillis()
)