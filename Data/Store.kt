package com.example.sodv3203_final_project.Data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stores")
data class Store(
    @PrimaryKey(autoGenerate = true) val storeId: Int = 0,
    val name: String,
    val address: String,
    val city: String,
    val distance: Double, // Distance in km
    val isOpen: Boolean = true,
    val openHours: String = "6:00 AM - 10:00 PM"
)
