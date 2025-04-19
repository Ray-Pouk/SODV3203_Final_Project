package com.example.sodv3203_final_project.Factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sodv3203_final_project.Data.MenuItemDao
import com.example.sodv3203_final_project.Data.Orders.OrderItemDao
import com.example.sodv3203_final_project.ui.checkout.CheckoutViewModel

class CheckoutViewModelFactory(
    private val orderItemDao: OrderItemDao,
    private val menuItemDao: MenuItemDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CheckoutViewModel::class.java)) {
            return CheckoutViewModel(orderItemDao, menuItemDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
