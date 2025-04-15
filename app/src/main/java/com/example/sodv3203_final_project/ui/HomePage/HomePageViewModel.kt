package com.example.sodv3203_final_project.ui.HomePage

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class MenuItem(
    val id: Int,
    val name: String,
    val description: String,
    val imageResId: Int
)

class HomePageViewModel : ViewModel() {

    private val _menuItems = MutableStateFlow<List<MenuItem>>(emptyList())
    val menuItems: StateFlow<List<MenuItem>> = _menuItems

    private val _locationInfo = MutableStateFlow("Tim Hortons - Open 6AM to 9PM")
    val locationInfo: StateFlow<String> = _locationInfo

    init {
        // Placeholder: Replace this with data from your database or API
        _menuItems.value = listOf(
            MenuItem(1, "Coffee", "Fresh brewed coffee", com.example.sodv3203_final_project.R.drawable.ic_launcher_foreground),
            MenuItem(2, "Bagel", "Toasted bagel with cream cheese", com.example.sodv3203_final_project.R.drawable.ic_launcher_foreground),
            MenuItem(3, "Breakfast Wrap", "Eggs, cheese, and sausage", com.example.sodv3203_final_project.R.drawable.ic_launcher_foreground)
        )
    }
}
