package com.example.sodv3203_final_project.ui.HomePage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sodv3203_final_project.Data.StoreLocation
import com.example.sodv3203_final_project.Data.StoreLocationDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomePageViewModel(
    private val storeLocationDao: StoreLocationDao
) : ViewModel() {

    private val _storeLocations = MutableStateFlow<List<StoreLocation>>(emptyList())
    val storeLocations: StateFlow<List<StoreLocation>> = _storeLocations

    init {
        viewModelScope.launch {
            storeLocationDao.getAllStores().collect { locations ->
                if (locations.isEmpty()) {
                    insertSampleStores()
                } else {
                    _storeLocations.value = locations
                }
            }
        }
    }

    private fun insertSampleStores() {
        viewModelScope.launch(Dispatchers.IO) {
            val sampleStores = listOf(
                StoreLocation(
                    name = "Tim Hortons - Main Street",
                    address = "123 Main St",
                    city = "Halifax",
                    postalCode = "B3J 1A1",
                    phoneNumber = "(902) 123-4567"
                ),
                StoreLocation(
                    name = "Tim Hortons - Waterfront",
                    address = "456 Ocean Dr",
                    city = "Halifax",
                    postalCode = "B3J 2B2",
                    phoneNumber = "(902) 234-5678"
                ),
                StoreLocation(
                    name = "Tim Hortons - North End",
                    address = "789 Gottingen St",
                    city = "Halifax",
                    postalCode = "B3K 3C3",
                    phoneNumber = "(902) 345-6789"
                )
            )

            sampleStores.forEach { storeLocationDao.insertStore(it) }

            // Fetch the inserted data again on the main thread and update UI
            val updatedStores = storeLocationDao.getAllStores().first()
            _storeLocations.value = updatedStores
        }
    }
}
