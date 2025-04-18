package com.example.sodv3203_final_project.Data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MenuCategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategory(category: MenuCategory): Long

    @Query("SELECT * FROM menu_categories")
    fun getAllCategories(): Flow<List<MenuCategory>>

    @Query("SELECT * FROM menu_categories WHERE categoryId = :categoryId")
    fun getCategoryById(categoryId: Int): MenuCategory

    @Delete
    fun deleteCategory(category: MenuCategory): Int
}
