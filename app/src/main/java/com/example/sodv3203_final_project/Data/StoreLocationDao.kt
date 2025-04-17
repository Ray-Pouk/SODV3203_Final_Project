package com.example.sodv3203_final_project.Data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface StoreLocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStore(store: StoreLocation): Long

    @Query("SELECT * FROM store_locations")
    fun getAllStores(): Flow<List<StoreLocation>>

    @Query("SELECT * FROM store_locations WHERE storeId = :storeId")
    fun getStoreById(storeId: Int): StoreLocation

    @Delete
    fun deleteStore(store: StoreLocation): Int  // Return Int (rows deleted)
}

