package com.example.sodv3203_final_project.ui.HomePage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sodv3203_final_project.Data.MenuCategory
import com.example.sodv3203_final_project.Data.MenuCategoryDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomePageViewModel(
    private val menuCategoryDao: MenuCategoryDao
) : ViewModel() {

    private val _menuCategories = MutableStateFlow<List<MenuCategory>>(emptyList())
    val menuCategories: StateFlow<List<MenuCategory>> = _menuCategories

    init {
        viewModelScope.launch {
            menuCategoryDao.getAllCategories().collect { categories ->
                if (categories.isEmpty()) {
                    insertSampleCategories()
                } else {
                    _menuCategories.value = categories
                }
            }
        }
    }

    private fun insertSampleCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            val sampleCategories = listOf(
                MenuCategory(name = "Beverages", description = "Hot and cold drinks"),
                MenuCategory(name = "Breakfast", description = "Morning favorites"),
                MenuCategory(name = "Bakery", description = "Freshly baked goods"),
                MenuCategory(name = "Lunch", description = "Sandwiches and wraps")
            )
            sampleCategories.forEach { menuCategoryDao.insertCategory(it) }
            _menuCategories.value = menuCategoryDao.getAllCategories().first()
        }
    }
}
