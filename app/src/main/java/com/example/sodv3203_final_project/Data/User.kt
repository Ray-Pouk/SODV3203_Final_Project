package com.example.sodv3203_final_project.Data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val userId: Int = 0,
    val fullName: String,
    val email: String,
    val password: String,
    val createdAt: Long = System.currentTimeMillis()
)
