package com.example.sodv3203_final_project.Factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sodv3203_final_project.Data.MenuCategoryDao
import com.example.sodv3203_final_project.ui.HomePage.HomePageViewModel

class HomePageViewModelFactory(
    private val menuCategoryDao: MenuCategoryDao
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomePageViewModel::class.java)) {
            return HomePageViewModel(menuCategoryDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

