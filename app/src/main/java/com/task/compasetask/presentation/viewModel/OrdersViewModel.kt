package com.task.compasetask.presentation.viewModel


import androidx.lifecycle.ViewModel
import com.task.compasetask.data.models.Order
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val cartManager: CartManager
) : ViewModel() {
    val orders: StateFlow<List<Order>> = cartManager.orders
}