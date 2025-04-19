package com.example.sodv3203_final_project.Data.Orders

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object CartManager {
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems

    fun addItem(item: CartItem) {
        _cartItems.value = _cartItems.value + item
    }

    fun removeItem(index: Int) {
        _cartItems.value = _cartItems.value.toMutableList().apply { removeAt(index) }
    }

    fun clearCart() {
        _cartItems.value = emptyList()
    }

    fun getSubtotal(): Double {
        return _cartItems.value.sumOf { it.finalPrice * it.quantity }
    }

    fun getAllItems(): List<CartItem> = _cartItems.value
}

