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
    private lateinit var uservm: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        uservm = ViewModelProvider(this)[UserViewModel::class.java]
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addNewUser()
        observeSignUpStatus()
        binding.tvSignin.setOnClickListener{
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
    }

    private fun addNewUser() {
        binding.btnSignup.setOnClickListener{
            binding.apply {
                val firstname = etFirstname.text.toString()
                val lastname = etLastname.text.toString()
                val gender = etGender.text.toString()
                val username = etPassword.text.toString()
                val password = etPassword.text.toString()
                val newuser = User(31,username+"@gmail.com",firstname,lastname,gender,username,password,"","")
                uservm.signUp(newuser)
            }
        }
    }

    private fun observeSignUpStatus() {
        uservm.signUpSucess.observe(this, Observer { success ->
            if (success) {
                Toast.makeText(this, "Sign Up Successful", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, SignInActivity::class.java)
                // Clear the navigation event to avoid navigating multiple times
                startActivity(intent)
            }
        })
    }
}