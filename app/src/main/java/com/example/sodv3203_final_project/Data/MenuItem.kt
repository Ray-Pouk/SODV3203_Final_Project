package com.example.sodv3203_final_project.Data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "menu_items")
data class MenuItem(
    @PrimaryKey(autoGenerate = true) val itemId: Int = 0,
    val name: String,
    val description: String,
    val price: Double,
    val category: String,
    val imageResId: Int
)
