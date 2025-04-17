package com.example.sodv3203_final_project.Data.Orders

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrder(order: Order): Long

    @Delete
    fun deleteOrder(order: Order): Int

    @Query("SELECT * FROM orders WHERE userId = :userId")
    fun getOrdersByUser(userId: Int): List<Order>

    @Query("SELECT * FROM orders WHERE orderId = :orderId")
    fun getOrderById(orderId: Int): Order

    @Query("SELECT * FROM orders")
    fun getAllOrders(): Flow<List<Order>>
}
