package com.example.todos.db

import com.example.todos.pojo.User
import javax.inject.Inject

class AuthRepository @Inject constructor(private val userDao: UserDao) {

    suspend fun signInUser(username: String, password: String): User {
        return userDao.getUser(username, password)
    }

    suspend fun insertUser(user : User) : Long{
        return userDao.insertUser(user)
    }

    suspend fun checkUsernameAvailability(username : String) : Boolean {
        val userExist = userDao.checkUsernameAvailability(username)
        return userExist == null
    }
}