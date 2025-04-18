package com.example.sodv3203_final_project.Factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sodv3203_final_project.Data.MenuItemDao
import com.example.sodv3203_final_project.Data.Orders.OrderCustomizationDao
import com.example.sodv3203_final_project.ui.Product.ProductPageViewModel

class ProductPageViewModelFactory(
    private val menuItemDao: MenuItemDao,
    private val customizationDao: OrderCustomizationDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ProductPageViewModel::class.java)) {
            ProductPageViewModel(menuItemDao, customizationDao) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}

