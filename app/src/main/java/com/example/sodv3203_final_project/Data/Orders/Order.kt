package com.example.sodv3203_final_project.Data.Orders

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class Order(
    @PrimaryKey(autoGenerate = true) val orderId: Int = 0,
    val userId: Int,
    val totalPrice: Double = 0.0,
    val status: String = "Pending",
    val createdAt: Long = System.currentTimeMillis()
)
