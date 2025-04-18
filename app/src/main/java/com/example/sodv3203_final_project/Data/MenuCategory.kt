package com.example.sodv3203_final_project.Data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "menu_categories")
data class MenuCategory(
    @PrimaryKey(autoGenerate = true) val categoryId: Int = 0,
    val name: String,
    val description: String
)
