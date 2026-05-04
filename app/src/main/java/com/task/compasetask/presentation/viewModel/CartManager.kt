package com.task.compasetask.presentation.viewModel

// CartManager.kt
import com.task.compasetask.data.models.CartItem
import com.task.compasetask.data.models.Order
import com.task.compasetask.data.models.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.toMutableList

@Singleton
class CartManager @Inject constructor() {
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    val orders: StateFlow<List<Order>> = _orders.asStateFlow()

    val cartItemCount = MutableStateFlow(0)
    val totalPrice = MutableStateFlow(0.0)

    private val _checkoutSuccess = MutableStateFlow(false)
    val checkoutSuccess: StateFlow<Boolean> = _checkoutSuccess.asStateFlow()

    init {
        updateTotals()
    }

    fun addToCart(product: Product) {
        val current = _cartItems.value.toMutableList()
        val existing = current.find { it.product.id == product.id }
        if (existing != null) existing.quantity++
        else current.add(CartItem(product, 1))
        _cartItems.value = current
        updateTotals()
    }

    fun removeFromCart(product: Product) {
        _cartItems.value = _cartItems.value.filter { it.product.id != product.id }
        updateTotals()
    }

    fun updateQuantity(product: Product, newQuantity: Int) {
        if (newQuantity <= 0) {
            removeFromCart(product)
            return
        }
        val current = _cartItems.value.toMutableList()
        current.find { it.product.id == product.id }?.quantity = newQuantity
        _cartItems.value = current
        updateTotals()
    }

    private fun updateTotals() {
        cartItemCount.value = _cartItems.value.sumOf { it.quantity }
        totalPrice.value = _cartItems.value.sumOf { it.product.price * it.quantity }
    }

    fun checkout() {
        if (_cartItems.value.isEmpty()) return
        val order = Order(
            items = _cartItems.value.toList(),
            totalPrice = totalPrice.value
        )
        _orders.value = _orders.value + order
        _cartItems.value = emptyList()
        updateTotals()
        _checkoutSuccess.value = true
    }

    fun resetCheckoutSuccess() {
        _checkoutSuccess.value = false
    }
}