package com.example.sodv3203_final_project.ui.Product

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sodv3203_final_project.Data.MenuItem
import com.example.sodv3203_final_project.Data.MenuItemDao
import com.example.sodv3203_final_project.Data.Orders.OrderCustomization
import com.example.sodv3203_final_project.Data.Orders.OrderCustomizationDao
import com.example.sodv3203_final_project.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ProductPageViewModel(
    private val menuItemDao: MenuItemDao,
    private val customizationDao: OrderCustomizationDao
) : ViewModel() {

    private val _menuItems = MutableStateFlow<List<MenuItem>>(emptyList())
    val menuItems: StateFlow<List<MenuItem>> = _menuItems.asStateFlow()

    private val _customizations = MutableStateFlow<List<OrderCustomization>>(emptyList())
    val customizations: StateFlow<List<OrderCustomization>> = _customizations.asStateFlow()

    init {
        insertDummyData()
        fetchMenuItems()
    }

    fun fetchMenuItemsByCategory(category: String) {
        viewModelScope.launch {
            try {
                menuItemDao.getItemsByCategory(category).collectLatest { items ->
                    _menuItems.value = items
                }
            } catch (e: Exception) {
                Log.e("ProductPageViewModel", "Error fetching items by category", e)
            }
        }
    }

    private fun fetchMenuItems() {
        viewModelScope.launch {
            try {
                Log.d("ProductPageViewModel", "Fetching menu items from database")
                menuItemDao.getAllMenuItems().collectLatest { items ->
                    Log.d("ProductPageViewModel", "Fetched ${items.size} items: $items")
                    _menuItems.value = items
                }
            } catch (e: Exception) {
                Log.e("ProductPageViewModel", "Error fetching menu items", e)
            }
        }
    }

    private fun insertDummyData() {
        viewModelScope.launch(Dispatchers.IO) {
            val existingItems = menuItemDao.getAllMenuItemsOnce()
            if (existingItems.isEmpty()) {
                Log.d("ProductPageViewModel", "Inserting dummy data into database")
                val dummyItems = listOf(
                    MenuItem(
                        name = "Coffee",
                        description = "Fresh brewed coffee",
                        price = 2.49,
                        category = "Beverages",
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
                dummyItems.forEach { menuItemDao.insertMenuItem(it) }
                Log.d("ProductPageViewModel", "Dummy data inserted successfully")
                fetchMenuItems()
            }
        }
    }

    // 👉 Load customizations (sizes) for a given menu item
    fun loadSizeOptions(menuItemId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val sizes = customizationDao.getCustomizationsByOrderItem(menuItemId)
                _customizations.value = sizes
            } catch (e: Exception) {
                Log.e("ProductPageViewModel", "Failed to load customizations", e)
            }
        }
    }
}
