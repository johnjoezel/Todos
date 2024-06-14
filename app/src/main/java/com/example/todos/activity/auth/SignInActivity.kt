package com.example.todos.activity.auth


import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.todos.UserRepository
import com.example.todos.UserViewModelFactory
import com.example.todos.databinding.ActivitySignInBinding
import com.example.todos.others.RetrofitInstance
import com.example.todos.viewModels.UserViewModel

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var userViewModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySignInBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val repository = UserRepository(RetrofitInstance.userApi)
        val viewModelFactory = UserViewModelFactory(repository)
        userViewModel = ViewModelProvider(this, viewModelFactory)[UserViewModel::class.java]
        userViewModel.users.observe(this) { users ->
            Log.d("JANJOEZEL", "onCreate: ${users}")
        }

        userViewModel.fetchUsers()

    }
}