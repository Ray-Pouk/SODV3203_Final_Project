package com.example.sodv3203_final_project.ui.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sodv3203_final_project.Data.Orders.OrderItemDao
import com.example.sodv3203_final_project.Data.MenuItem
import com.example.sodv3203_final_project.Data.MenuItemDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CheckoutViewModel(
    private val orderItemDao: OrderItemDao,
    private val menuItemDao: MenuItemDao
) : ViewModel() {

    private val _cartItems = MutableStateFlow<List<MenuItem>>(emptyList())
    val cartItems: StateFlow<List<MenuItem>> get() = _cartItems

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                loadCartItems()
            }
        }
    }

    // Load cart items based on the current user's order
    private suspend fun loadCartItems() {
        val orderItems = orderItemDao.getOrderItemsByOrderId(orderId = 1)

        val menuItems = mutableListOf<MenuItem>()
        for (orderItem in orderItems) {
            val menuItem = menuItemDao.getMenuItemById(orderItem.itemId)
            if (menuItem != null) {
                repeat(orderItem.quantity) {
                    menuItems.add(menuItem)
                }
            }
        }

        _cartItems.value = menuItems
    }

    fun getCartTotal(): Double {
        return _cartItems.value.sumOf { it.price }
    }
}
