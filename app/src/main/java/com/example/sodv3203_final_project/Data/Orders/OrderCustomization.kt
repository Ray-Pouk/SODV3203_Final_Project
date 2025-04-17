package com.example.sodv3203_final_project.Data.Orders

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "order_customizations")
data class OrderCustomization(
    @PrimaryKey(autoGenerate = true) val customizationId: Int = 0,
    val orderItemId: Int,
    val customizationName: String,
    val customizationPrice: Double
)
