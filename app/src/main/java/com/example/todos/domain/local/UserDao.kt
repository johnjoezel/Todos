package com.example.todos.domain.local

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.todos.domain.pojo.User

@Dao
interface UserDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<User>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User) : Long

    @Query("UPDATE users SET imgUri = :imageUri WHERE userId = :userId")
    suspend fun updateUser(userId : Int, imageUri : Uri?)

    @Query("SELECT * FROM users WHERE username = :username AND password = :password")
    suspend fun getUser(username: String, password: String): User

    @Query("SELECT * FROM users WHERE userId = :userId")
    suspend fun getUser(userId: Int): User

    @Query("SELECT * FROM users")
    fun getUsers() : LiveData<User>

    @Query("SELECT username FROM users WHERE username = :username")
    suspend fun checkUsernameAvailability(username : String) : String?
}