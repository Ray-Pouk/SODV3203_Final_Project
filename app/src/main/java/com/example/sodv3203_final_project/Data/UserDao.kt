package com.example.sodv3203_final_project.Data

import androidx.room.*

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User): Long

    @Query("SELECT * FROM users WHERE email = :email")
    fun getUserByEmail(email: String): User?

    @Transaction
    fun insertDummyUser() {
        val dummyUser = User(
            fullName = "Test User",
            email = "test@example.com",
            password = "password"
        )
        insertUser(dummyUser)
    }
}



