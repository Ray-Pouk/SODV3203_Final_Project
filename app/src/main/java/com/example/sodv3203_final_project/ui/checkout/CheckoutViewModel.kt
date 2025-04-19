package com.example.sodv3203_final_project.ui.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sodv3203_final_project.Data.Orders.CartItem
import com.example.sodv3203_final_project.Data.Orders.CartManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CheckoutViewModel : ViewModel() {

    // StateFlow to observe the cart items in the ViewModel
    private val _cartItems = MutableStateFlow(CartManager.cartItems.value)
    val cartItems: StateFlow<List<CartItem>> = _cartItems

    init {
        // Observe changes in the CartManager and update the ViewModel state accordingly
        viewModelScope.launch {
            CartManager.cartItems.collect { items ->
                _cartItems.value = items
            }
        }
    }

    fun clearCart() {
        CartManager.clearCart()
    }
}
