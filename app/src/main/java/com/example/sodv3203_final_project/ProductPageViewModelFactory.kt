package com.example.sodv3203_final_project

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sodv3203_final_project.Data.MenuItemDao
import com.example.sodv3203_final_project.ui.Product.ProductPageViewModel

class ProductPageViewModelFactory(private val dao: MenuItemDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ProductPageViewModel::class.java)) {
            ProductPageViewModel(dao) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
