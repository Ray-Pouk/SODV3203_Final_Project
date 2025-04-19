package com.example.sodv3203_final_project.Data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ratings")
data class Rating(
    @PrimaryKey(autoGenerate = true) val ratingId: Int = 0,
    val userId: Int,
    val itemId: Int,
    val ratingValue: Int, // Usually 1 to 5
    val review: String,
    val createdAt: Long = System.currentTimeMillis()
)
