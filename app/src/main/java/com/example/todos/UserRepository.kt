package com.example.todos

import android.util.Log
import com.example.todos.apis.RemoteApi
import com.example.todos.db.UserDao
import com.example.todos.pojo.User
import com.example.todos.pojo.UserResponse

class UserRepository(private val userDao: UserDao, private val remoteApi: RemoteApi) {

    suspend fun fetchAndStoreUsers() : List<User> {
        val usersFromApi = remoteApi.getAllUsersFromApi()
        Log.d("UserNiCla", "fetchAndStoreUsers: ${usersFromApi}")
        val storeThereUsers = usersFromApi.users.map{ mapToUser(it)}
        userDao.insertAll(storeThereUsers)
        return usersFromApi.users
    }

    private fun mapToUser(user: User): User {
        return User(
            id = user.id,
            firstName = user.firstName,
            lastName = user.lastName,
            gender = user.gender,
            email = user.email,
            username = user.username,
            password = user.password,
        )
    }


}