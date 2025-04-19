package com.example.sodv3203_final_project.Data.Orders

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.sodv3203_final_project.Data.Converters

@Entity(tableName = "order_items")
data class OrderItem(
    @PrimaryKey(autoGenerate = true) val orderItemId: Int = 0,
    val name: String,
    val price: Double,
    val orderId: Int,
    val instructions: String = "",

    @TypeConverters(Converters::class)
    val customizations: List<OrderCustomization> = emptyList()
)
