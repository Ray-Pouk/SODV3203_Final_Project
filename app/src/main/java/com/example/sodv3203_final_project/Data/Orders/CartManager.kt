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
        if (index >= 0 && index < _cartItems.value.size) {
            _cartItems.value = _cartItems.value.toMutableList().apply { removeAt(index) }
        }
    }


    fun removeItem(item: CartItem) {
        val index = _cartItems.value.indexOf(item)
        if (index >= 0) {
            removeItem(index)
        }
    }


    fun removeItemById(menuItemId: Int) {
        val index = _cartItems.value.indexOfFirst { it.menuItemId == menuItemId }
        if (index >= 0) {
            removeItem(index)
        }
    }

    fun clearCart() {
        _cartItems.value = emptyList()
    }

    fun getSubtotal(): Double {
        return _cartItems.value.sumOf { it.finalPrice * it.quantity }
    }

    fun getAllItems(): List<CartItem> = _cartItems.value


    fun getItemCount(): Int = _cartItems.value.size
}
