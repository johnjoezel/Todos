package com.example.todos

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.todos.activity.auth.SignInActivity
import com.example.todos.databinding.ActivitySplashScreenBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {

    companion object {
        const val SPLASH_SCREEN_SHOWN = "splashScreenShown"
        private const val SPLASH_SCREEN_DURATION_MS = 2000L
    }

    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences(SignInActivity.PREF_NAME, Context.MODE_PRIVATE)

        if(isSplashScreenShown()){
            redirectToMain()
        } else {
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
            redirectToMain()
        }
    }

    private fun redirectToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun flagSplashScreenAsShown() {
        val editor = sharedPreferences.edit()
        editor.putBoolean(SPLASH_SCREEN_SHOWN, true)
        editor.apply()
    }


}