package com.example.todos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todos.activity.auth.SignInActivity
import com.example.todos.adapters.TodoAdapter
import com.example.todos.databinding.ActivityMainBinding
import com.example.todos.db.AppDatabase
import com.example.todos.others.RetrofitInstance
import com.example.todos.others.SwipeToDeleteHelper
import com.example.todos.pojo.Todo
import com.example.todos.pojo.User
import com.example.todos.viewModels.AuthViewModel
import com.example.todos.viewModels.TodoViewModel
import com.example.todos.viewModels.UserViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var userViewModel: UserViewModel
    private lateinit var authViewModel: AuthViewModel
    private lateinit var currentUser: User
    private val PREF_NAME = "MyPrefs"
    private val KEY_IS_LOGGED_IN = "isLoggedIn"
    private val TAG = "MAIN_ACTIVITY"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = AppDatabase.getInstance(applicationContext)
        val userDao = db.userDao()
        val repository = UserRepository(userDao,RetrofitInstance.userApi)
        val viewModelFactory = UserViewModelFactory(repository)
        val authRepository = AuthRepository(userDao)
        val authViewModelFactory = AuthViewModelFactory(authRepository)
        authViewModel = ViewModelProvider(this, authViewModelFactory)[AuthViewModel::class.java]
        userViewModel = ViewModelProvider(this, viewModelFactory)[UserViewModel::class.java]
        userViewModel.fetchUsers()
        if(!isLoggedIn()){
            redirectToLogin()
            return
        }


        val navHostFragment = supportFragmentManager.findFragmentById(R.id.mainFragment) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomnav.setupWithNavController(navController)
    }

    private fun redirectToLogin() {
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
    }

    private fun isLoggedIn(): Boolean {
        val sharedPreferences = getSharedPreferences(SignInActivity.PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(SignInActivity.PREF_KEY_IS_LOGGED_IN, false)
    }

}