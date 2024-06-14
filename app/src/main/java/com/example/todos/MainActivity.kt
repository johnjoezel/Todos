package com.example.todos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.todos.activity.auth.SignInActivity
import com.example.todos.databinding.ActivityMainBinding
import com.example.todos.db.AppDatabase
import com.example.todos.others.RetrofitInstance
import com.example.todos.pojo.User
import com.example.todos.viewModels.AuthViewModel
import com.example.todos.viewModels.TodoViewModel
import com.example.todos.viewModels.UserViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var userViewModel: UserViewModel
    private lateinit var authViewModel: AuthViewModel
    private lateinit var todoViewModel: TodoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = AppDatabase.getInstance(applicationContext)
        val userDao = db.userDao()
        val todoDao = db.todoDao()
        val repository = Repository(todoDao,userDao,RetrofitInstance.userApi)
        val viewModelFactory = UserViewModelFactory(repository)
        val authRepository = AuthRepository(userDao)
        val authViewModelFactory = AuthViewModelFactory(authRepository)
        authViewModel = ViewModelProvider(this, authViewModelFactory)[AuthViewModel::class.java]
        userViewModel = ViewModelProvider(this, viewModelFactory)[UserViewModel::class.java]
        userViewModel.fetchUsers()
        todoViewModel.fetchTodos()
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