package com.example.sodv3203_final_project.Data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "store_locations")
data class StoreLocation(
    @PrimaryKey(autoGenerate = true) val storeId: Int = 0,
    val name: String,
    val address: String,
    val city: String,
    val postalCode: String,
    val phoneNumber: String
)
