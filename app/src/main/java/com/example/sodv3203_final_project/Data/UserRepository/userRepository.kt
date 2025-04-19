package com.example.sodv3203_final_project.Data.UserRepository

import com.example.sodv3203_final_project.Data.User
import com.example.sodv3203_final_project.Data.UserDao

class UserRepository(private val userDao: UserDao) {

    suspend fun registerUser(fullName: String, email: String, password: String): Boolean {
        return try {
            val user = User(fullName = fullName, email = email, password = password)
            userDao.insertUser(user)
            true
        } catch (e: Exception) {
            false
        }
    }
}
