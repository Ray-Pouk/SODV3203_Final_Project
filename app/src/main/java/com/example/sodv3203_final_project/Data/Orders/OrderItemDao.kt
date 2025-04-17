package com.example.sodv3203_final_project.Data.Orders

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrderItem(orderItem: OrderItem): Long

    @Query("SELECT * FROM order_items WHERE orderId = :orderId")
    fun getOrderItemsByOrderId(orderId: Int): List<OrderItem>

    @Delete
    fun deleteOrderItem(orderItem: OrderItem): Int
}
