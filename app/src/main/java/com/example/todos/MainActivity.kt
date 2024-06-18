package com.example.todos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.todos.activity.auth.SignInActivity
import com.example.todos.databinding.ActivityMainBinding
import com.example.todos.db.AppDatabase
import com.example.todos.db.Repository
import com.example.todos.others.RetrofitInstance
import com.example.todos.viewModels.UserViewModel
import com.example.todos.viewmodelfactory.UserViewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var userViewModel: UserViewModel
    private var userId : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreferences = getSharedPreferences(SignInActivity.PREF_NAME, Context.MODE_PRIVATE)
        userId = sharedPreferences.getInt(SignInActivity.PREF_KEY_USER_ID,-1)
        val db = AppDatabase.getInstance(applicationContext)
        val userDao = db.userDao()
        val todoDao = db.todoDao()
        val repository = Repository(todoDao,userDao,RetrofitInstance.userApi)
        val viewModelFactory = UserViewModelFactory(repository)
        userViewModel = ViewModelProvider(this, viewModelFactory)[UserViewModel::class.java]
        userViewModel.fetchUsers()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkLoginStatus()
    }

    private fun checkLoginStatus() {
        binding.circularProgress.visibility = View.VISIBLE
        lifecycleScope.launch {
            delay(2000L)
            binding.circularProgress.visibility = View.GONE
            if (isLoggedIn()){
                setupBottomNavigation()
            } else {
                redirectToLogin()
            }
        }
    }

    private fun setupBottomNavigation() {
        //bottom navigation
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.mainFragment) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomnav.setupWithNavController(navController)
    }

    private fun isLoggedIn(): Boolean {
        return getSharedPreferences(SignInActivity.PREF_NAME, Context.MODE_PRIVATE).getBoolean(SignInActivity.PREF_KEY_IS_LOGGED_IN, false)
    }

    private fun redirectToLogin() {
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
    }
}