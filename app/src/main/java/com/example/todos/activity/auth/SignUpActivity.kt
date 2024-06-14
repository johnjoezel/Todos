package com.example.todos.activity.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.todos.MainActivity
import com.example.todos.databinding.ActivitySignUpBinding
import com.example.todos.others.Utilities
import com.example.todos.pojo.User
import com.example.todos.viewModels.UserViewModel

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.tvSignin.setOnClickListener{
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
    }
}