package com.example.todos.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.todos.databinding.ActivitySplashScreenBinding
import com.example.todos.domain.local.AppDatabase
import com.example.todos.util.helper.SharedPreferenceHelper
import com.example.todos.viewmodels.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MAIN_ACTIVITY"
        private const val SPLASH_SCREEN_DURATION_MS = 2000L
    }

    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var userViewModel: UserViewModel

    @Inject
    lateinit var sharedPreferenceHelper: SharedPreferenceHelper

    @Inject
    lateinit var appDatabase: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
//        if(isSplashScreenShown()){
//            redirectToLogin()
//        } else {
//            userViewModel.fetchUsers()
//            showSplashScreen()
//            flagSplashScreenAsShown()
//        }
    }

    private fun isSplashScreenShown() : Boolean{
       return sharedPreferenceHelper.isSplashScreenShown
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
        sharedPreferenceHelper.isSplashScreenShown = true
    }
}