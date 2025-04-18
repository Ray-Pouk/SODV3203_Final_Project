package com.example.sodv3203_final_project.Data

import androidx.room.*

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDummyUser() {
        insertUser(User(fullName = "Test User", email = "test@example.com", password = "password123"))
    }

    @Query("SELECT * FROM users WHERE email = :email")
    fun getUserByEmail(email: String): User?
}




