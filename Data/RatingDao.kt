package com.example.sodv3203_final_project.Data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface RatingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRating(rating: Rating): Long

    @Query("SELECT * FROM ratings WHERE itemId = :itemId")
    fun getRatingsForItem(itemId: Int): List<Rating>

    @Query("SELECT * FROM ratings WHERE userId = :userId")
    fun getRatingsByUser(userId: Int): List<Rating>

    @Delete
    fun deleteRating(rating: Rating): Int
}

