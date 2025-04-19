package com.example.sodv3203_final_project.Data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface StoreDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStore(store: Store): Long

    @Query("SELECT * FROM stores ORDER BY distance ASC")
    fun getAllStores(): Flow<List<Store>>

    @Query("SELECT * FROM stores WHERE storeId = :storeId")
    fun getStoreById(storeId: Int): Store?

    @Delete
    fun deleteStore(store: Store): Int
}