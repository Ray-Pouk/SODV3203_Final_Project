package com.example.sodv3203_final_project.ui.Product

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sodv3203_final_project.Data.MenuItem
import com.example.sodv3203_final_project.Data.MenuItemDao
import com.example.sodv3203_final_project.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ProductPageViewModel(private val dao: MenuItemDao) : ViewModel() {

    private val _menuItems = MutableStateFlow<List<MenuItem>>(emptyList())
    val menuItems: StateFlow<List<MenuItem>> = _menuItems.asStateFlow()

    init {
        // Try inserting dummy data and then fetching items
        insertDummyData()
        fetchMenuItems()
    }

    private fun fetchMenuItems() {
        // Run database access in background thread using coroutine
        viewModelScope.launch {
            try {
                // Log fetching
                Log.d("ProductPageViewModel", "Fetching menu items from database")

                // Fetch data from the database
                dao.getAllMenuItems().collectLatest { items ->
                    // Log the fetched items
                    Log.d("ProductPageViewModel", "Fetched ${items.size} items: $items")
                    _menuItems.value = items
                }
            } catch (e: Exception) {
                // Log or handle any database or data retrieval errors
                Log.e("ProductPageViewModel", "Error fetching menu items", e)
            }
        }
    }

    private fun insertDummyData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val existingItems = dao.getAllMenuItemsOnce()
                if (existingItems.isEmpty()) {
                    Log.d("ProductPageViewModel", "Inserting dummy data into database")

                    val dummyItems = listOf(
                        MenuItem(
                            name = "Coffee",
                            description = "Fresh brewed coffee",
                            price = 2.49,
                            category = "Drinks",
                            imageResId = R.drawable.placeholder
                        ),
                        MenuItem(
                            name = "Bagel",
                            description = "Toasted bagel with cream cheese",
                            price = 3.29,
                            category = "Bakery",
                            imageResId = R.drawable.placeholder
                        ),
                        MenuItem(
                            name = "Wrap",
                            description = "Sausage and egg wrap",
                            price = 4.49,
                            category = "Breakfast",
                            imageResId = R.drawable.placeholder
                        )
                    )

                    dummyItems.forEach { dao.insertMenuItem(it) }

                    Log.d("ProductPageViewModel", "Dummy data inserted successfully")
                    fetchMenuItems()
                } else {
                    Log.d("ProductPageViewModel", "Dummy data already exists, skipping insertion")
                }
            } catch (e: Exception) {
                Log.e("ProductPageViewModel", "Error inserting dummy data", e)
            }
        }
    }
}

