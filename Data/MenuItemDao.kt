    package com.example.sodv3203_final_project.Data

    import androidx.room.*
    import kotlinx.coroutines.flow.Flow

    @Dao
    interface MenuItemDao {

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun insertMenuItem(menuItem: MenuItem): Long

        @Delete
        fun deleteMenuItem(item: MenuItem): Int

        @Query("SELECT * FROM menu_items")
        fun getAllMenuItems(): Flow<List<MenuItem>>

        @Query("SELECT * FROM menu_items")
        fun getAllMenuItemsOnce(): List<MenuItem>

        @Query("SELECT * FROM menu_items WHERE category = :categoryName")
        fun getItemsByCategory(categoryName: String): Flow<List<MenuItem>>

        @Query("SELECT * FROM menu_items WHERE id = :id")
        fun getMenuItemById(id: Int): MenuItem?
    }

