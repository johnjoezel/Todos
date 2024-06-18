package com.example.todos.activity.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.todos.db.AuthRepository
import com.example.todos.viewmodelfactory.AuthViewModelFactory
import com.example.todos.databinding.ActivitySignUpBinding
import com.example.todos.db.AppDatabase
import com.example.todos.others.TextUtilities
import com.example.todos.pojo.User
import com.example.todos.viewModels.AuthViewModel

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private val textUtilities = TextUtilities()
    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = AppDatabase.getInstance(applicationContext)
        val userDao = db.userDao()
        val repository = AuthRepository(userDao)
        val viewModelFactory = AuthViewModelFactory(repository)
        authViewModel = ViewModelProvider(this, viewModelFactory)[AuthViewModel::class.java]

        binding.btnSignup.setOnClickListener{
            verifyUserData()
        }

        binding.tvSignin.setOnClickListener{
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }

        authViewModel.returnLong.observe(this){long ->
            if(long != null){
                val intent = Intent(this, SignInActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun verifyUserData() {
        val firstname = binding.etFirstname.text.toString()
        val lastname = binding.etLastname.text.toString()
        val gender = binding.etGender.text.toString()
        val email = binding.etEmail.text.toString()
        val username = binding.etUsername.text.toString()
        val password = binding.etPassword.text.toString()
        val user = User(
            firstName = firstname,
            lastName = lastname,
            gender = gender,
            email = email,
            username = username,
            password = password)

        authViewModel.signUp(user)
    }

//    private fun emptyInputs() {
//        val emptyInputs = textUtilities.getEmptyInputIndices(
//            binding.etUsername.text.toString(),
//            binding.etPassword.text.toString()
//        )
//
//        if(emptyInputs.isNotEmpty()){
//            emptyInputs.forEach { index ->
//                when (index) {
//                    0 -> {
//                        binding.etUsername.setBackgroundResource(R.drawable.red_border)
//                    }
//                    1 -> {
//                        binding.etPassword.setBackgroundResource(R.drawable.red_border)
//                    }
//                    else -> return
//                }
//            }
//        } else {
//
//        }
//    }
}