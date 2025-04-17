package com.example.sodv3203_final_project.Data.Orders

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderCustomizationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCustomization(customization: OrderCustomization): Long

    @Query("SELECT * FROM order_customizations WHERE orderItemId = :orderItemId")
    fun getCustomizationsByOrderItem(orderItemId: Int): List<OrderCustomization>

    @Delete
   fun deleteCustomization(customization: OrderCustomization): Int
}
