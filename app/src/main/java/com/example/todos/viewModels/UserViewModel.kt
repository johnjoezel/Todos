package com.example.todos.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todos.others.RetrofitInstance
import com.example.todos.pojo.SignInRequestBody
import com.example.todos.pojo.User
import com.example.todos.pojo.Users
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel(): ViewModel() {

    private var userListLiveData = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = userListLiveData


    private val _signUpSucess = MutableLiveData<Boolean>()
    val signUpSucess: LiveData<Boolean>
        get() = _signUpSucess

    private val _userId = MutableLiveData<Int>()
    val userId: LiveData<Int> get() = _userId


    fun checkUsernameAvailability(username:String){
        RetrofitInstance.userApi.getAllUsers().enqueue(object: Callback<Users>{
            override fun onResponse(call: Call<Users>, response: Response<Users>) {
                if(response.body()!=null){
                    val usernames = response.body()!!.users.map { it.username }
                    for (existingusername in usernames){
                        if(username == existingusername){
                            return
                        }else{

                        }
                    }
                } else {
                    return
                }
            }

            override fun onFailure(call: Call<Users>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun signUp(user: User){
        RetrofitInstance.userApi.addUser(user).enqueue(object: Callback<User>{
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val addedUser: User? = response.body()
                    Log.d("New User", "onResponse: ${addedUser}")
                    _signUpSucess.value = true
                } else {
                    // Handle error
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun signIn(username : String, password : String){
        val requestBody = SignInRequestBody(username, password)
        RetrofitInstance.userApi.signInUser(requestBody).enqueue(object: Callback<User>{
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val currentUser: User? = response.body()
                    if (currentUser != null) {
                        _userId.value = currentUser.id

                    }
                } else {

                    Log.e("Sign In Error", "Sign-in request failed with code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}