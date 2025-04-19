package com.example.sodv3203_final_project.ui.StoreLocation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sodv3203_final_project.Data.Store
import com.example.sodv3203_final_project.Data.StoreDao
import com.example.sodv3203_final_project.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StoreLocationViewModel(private val storeDao: StoreDao) : ViewModel() {

    private val _stores = MutableStateFlow<List<Store>>(emptyList())
    val stores: StateFlow<List<Store>> = _stores.asStateFlow()

    private val _selectedStore = MutableStateFlow<Store?>(null)
    val selectedStore: StateFlow<Store?> = _selectedStore.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    init {
        loadStores()

        // If there are no stores, put in random data
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val existingStores = storeDao.getAllStores().collectLatest { stores ->
                    if (stores.isEmpty()) {
                        insertSampleStores()
                    }
                    _stores.value = stores
                }
            }
        }
    }

    private fun loadStores() {
        viewModelScope.launch {
            storeDao.getAllStores().collectLatest { stores ->
                _stores.value = stores
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun selectStore(store: Store) {
        _selectedStore.value = store
    }

    private suspend fun insertSampleStores() {
        val sampleStores = listOf(
            Store(
                name = "Tim Hortons - Downtown",
                address = "123 Main Street",
                city = "Calgary, AB",
                distance = 0.8,
                isOpen = true,
                openHours = "24 hours"
            ),
            Store(
                name = "Tim Hortons - University",
                address = "456 College Ave",
                city = "Calgary, AB",
                distance = 1.5,
                isOpen = true,
                openHours = "6:00 AM - 11:00 PM"
            ),
            Store(
                name = "Tim Hortons - Bowness",
                address = "789 Bowness Road NW",
                city = "Calgary, AB",
                distance = 3.2,
                isOpen = true,
                openHours = "5:00 AM - 10:00 PM"
            ),
            Store(
                name = "Tim Hortons - Crowfoot",
                address = "321 Crowfoot Crescent NW",
                city = "Calgary, AB",
                distance = 4.7,
                isOpen = true,
                openHours = "6:00 AM - 11:00 PM"
            ),
            Store(
                name = "Tim Hortons - Deerfoot Meadows",
                address = "654 Deerfoot Trail SE",
                city = "Calgary, AB",
                distance = 5.3,
                isOpen = true,
                openHours = "5:30 AM - 10:00 PM"
            )
        )

        withContext(Dispatchers.IO) {
            sampleStores.forEach { storeDao.insertStore(it) }
        }
    }
}