package com.example.todos.activity.auth

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.todos.MainActivity
import com.example.todos.databinding.ActivitySignInBinding
import com.example.todos.viewModels.UserViewModel

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var uservm: UserViewModel
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        uservm = ViewModelProvider(this)[UserViewModel::class.java]
        binding = ActivitySignInBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        checkUser()
        observeSignInStatus()
    }

    private fun observeSignInStatus() {
        uservm.userId.observe(this, Observer { userId ->
            if (userId != null) {
                sharedPreferences = getSharedPreferences("my_shared_prefs", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putInt("userId", userId)
                editor.apply()
                val intent = Intent(this, MainActivity::class.java)
                // Clear the navigation event to avoid navigating multiple times
                startActivity(intent)
            }
        })
    }

    private fun checkUser() {
        binding.btnSignin.setOnClickListener{
            binding.apply {
                val username = etUsername.text.toString()
                val password = etPassword.text.toString()
                uservm.signIn(username, password)
            }
        }
    }
}