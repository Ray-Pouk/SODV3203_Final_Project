package com.example.sodv3203_final_project.Factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sodv3203_final_project.Data.StoreLocationDao
import com.example.sodv3203_final_project.ui.HomePage.HomePageViewModel

class HomePageViewModelFactory(
    private val storeLocationDao: StoreLocationDao
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomePageViewModel::class.java)) {
            return HomePageViewModel(storeLocationDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

