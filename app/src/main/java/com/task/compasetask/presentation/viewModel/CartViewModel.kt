package com.task.compasetask.presentation.viewModel


import androidx.lifecycle.ViewModel
import com.task.compasetask.data.models.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartManager: CartManager
) : ViewModel() {
    val cartItems = cartManager.cartItems
    val cartItemCount = cartManager.cartItemCount
    val totalPrice = cartManager.totalPrice
    val checkoutSuccess = cartManager.checkoutSuccess

    fun addToCart(product: Product) = cartManager.addToCart(product)
    fun removeFromCart(product: Product) = cartManager.removeFromCart(product)
    fun updateQuantity(product: Product, quantity: Int) = cartManager.updateQuantity(product, quantity)
    fun checkout() = cartManager.checkout()
    fun resetCheckoutSuccess() = cartManager.resetCheckoutSuccess()
}