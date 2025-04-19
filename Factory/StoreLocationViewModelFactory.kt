package com.example.sodv3203_final_project.Factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sodv3203_final_project.Data.StoreDao
import com.example.sodv3203_final_project.ui.StoreLocation.StoreLocationViewModel

class StoreLocationViewModelFactory(
    private val storeDao: StoreDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StoreLocationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return StoreLocationViewModel(storeDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
