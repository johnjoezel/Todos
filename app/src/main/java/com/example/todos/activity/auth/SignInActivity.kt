package com.example.todos.activity.auth


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.todos.AuthRepository
import com.example.todos.AuthViewModelFactory
import com.example.todos.MainActivity
import com.example.todos.databinding.ActivitySignInBinding
import com.example.todos.db.AppDatabase
import com.example.todos.viewModels.AuthViewModel

class SignInActivity : AppCompatActivity() {

    companion object {
        const val PREF_NAME = "MyPrefs"
        const val PREF_KEY_USER_ID = "userId"
        const val PREF_KEY_IS_LOGGED_IN = "isLoggedIn"
    }

    private lateinit var binding: ActivitySignInBinding
    private lateinit var authViewModel: AuthViewModel
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySignInBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val db = AppDatabase.getInstance(applicationContext)
        val userDao = db.userDao()
        val repository = AuthRepository(userDao)
        val viewModelFactory = AuthViewModelFactory(repository)
        authViewModel = ViewModelProvider(this, viewModelFactory)[AuthViewModel::class.java]


        authViewModel.loginUser.observe(this){ user ->
            if(user!=null){
                val userId = user.userId
                saveLoginStatus(true, userId)
                redirectToMain()
            }
        }

        binding.btnSignin.setOnClickListener{
            authViewModel.signIn(binding.etUsername.text.toString(), binding.etPassword.text.toString())
        }

        binding.tvSignup.setOnClickListener{
            redirectToSignUp()
        }
    }

    private fun redirectToSignUp() {
        val intent = Intent(this, SignUpActivity::class.java)
        // Clear the navigation event to avoid navigating multiple times
        startActivity(intent)
        finish()
    }

    private fun redirectToMain() {
        val intent = Intent(this, MainActivity::class.java)
        // Clear the navigation event to avoid navigating multiple times
        startActivity(intent)
        finish()
    }

    private fun saveLoginStatus(isLoggedIn: Boolean, userId: Int) {
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean(PREF_KEY_IS_LOGGED_IN, isLoggedIn)
        editor.putInt(PREF_KEY_USER_ID, userId)
        editor.apply()
    }
}