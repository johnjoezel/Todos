package com.example.todos.db

import com.example.todos.db.UserDao
import com.example.todos.pojo.User

class AuthRepository(private val userDao: UserDao) {

    suspend fun signInUser(username: String, password: String): User {
        return userDao.getUser(username, password)
    }

    suspend fun insertUser(user : User) : Long{
        return userDao.insertUser(user)
    }
}