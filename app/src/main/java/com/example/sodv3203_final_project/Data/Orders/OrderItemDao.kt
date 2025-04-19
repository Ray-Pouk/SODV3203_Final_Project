package com.example.sodv3203_final_project.Data.Orders

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrderItem(orderItem: OrderItem): Long

    @Query("SELECT * FROM order_items WHERE orderItemId = :orderId")
    fun getOrderItemsByOrderId(orderId: Int): List<OrderItem>

    @Delete
    fun deleteOrderItem(orderItem: OrderItem): Int

    @Query("DELETE FROM order_items WHERE orderItemId = :orderId")
    fun deleteOrderItemsByOrderId(orderId: Int)

    // Query to fetch customizations for an order item
    @Transaction
    @Query("SELECT * FROM order_customizations WHERE orderItemId = :orderItemId")
    fun getCustomizationsForOrderItem(orderItemId: Int): List<OrderCustomization>
}
