package com.example.sodv3203_final_project.Data.Orders

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "order_items")
data class OrderItem(
    @PrimaryKey(autoGenerate = true) val orderItemId: Int = 0,
    val orderId: Int,
    val itemId: Int,
    val quantity: Int
)
