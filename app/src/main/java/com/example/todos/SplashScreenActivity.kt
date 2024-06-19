package com.example.todos

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.todos.activity.auth.SignInActivity
import com.example.todos.databinding.ActivitySplashScreenBinding
import com.example.todos.db.AppDatabase
import com.example.todos.db.Repository
import com.example.todos.others.RetrofitInstance
import com.example.todos.viewModels.UserViewModel
import com.example.todos.viewmodelfactory.UserViewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MAIN_ACTIVITY"
        const val SPLASH_SCREEN_SHOWN = "splashScreenShown"
        private const val SPLASH_SCREEN_DURATION_MS = 2000L
    }

    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val db = AppDatabase.getInstance(applicationContext)
        val userDao = db.userDao()
        val todoDao = db.todoDao()
        val repository = Repository(todoDao,userDao, RetrofitInstance.userApi)
        val viewModelFactory = UserViewModelFactory(repository)
        userViewModel = ViewModelProvider(this, viewModelFactory)[UserViewModel::class.java]
        sharedPreferences = getSharedPreferences(SignInActivity.PREF_NAME, Context.MODE_PRIVATE)
        if(isSplashScreenShown()){
            redirectToLogin()
        } else {
            userViewModel.fetchUsers()
            showSplashScreen()
            flagSplashScreenAsShown()
        }
    }

    private fun isSplashScreenShown() : Boolean{
       return sharedPreferences.getBoolean(SPLASH_SCREEN_SHOWN, false)
    }

    private fun showSplashScreen() {
        lifecycleScope.launch {
            delay(SPLASH_SCREEN_DURATION_MS)
            redirectToLogin()
        }
    }

    private fun redirectToLogin() {
        startActivity(Intent(this, SignInActivity::class.java))
    }

    private fun flagSplashScreenAsShown() {
        val editor = sharedPreferences.edit()
        editor.putBoolean(SPLASH_SCREEN_SHOWN, true)
        editor.apply()
    }
}