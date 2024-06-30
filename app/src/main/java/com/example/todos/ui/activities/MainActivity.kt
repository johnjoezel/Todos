package com.example.todos.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.todos.R
import com.example.todos.data.local.AppDatabase
import com.example.todos.data.pojo.Todo
import com.example.todos.databinding.ActivityMainBinding
import com.example.todos.util.helper.SharedPreferenceHelper
import com.example.todos.viewmodels.TodoViewModel
import com.example.todos.viewmodels.UserViewModel
import com.google.android.material.shape.MaterialShapeDrawable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var todoViewModel: TodoViewModel

    @Inject
    lateinit var sharedPreferenceHelper: SharedPreferenceHelper
    @Inject
    lateinit var appDatabase: AppDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val onBackPressedCallback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                moveTaskToBack(true)
            }
        }
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        todoViewModel = ViewModelProvider(this)[TodoViewModel::class.java]

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onFabClick()
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.mainFragment) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomnav.setupWithNavController(navController)

    }

    private fun onFabClick() {
        binding.btnAddTaskActivity.setOnClickListener{
            val intent = Intent(this, AddTaskActivity::class.java)
            startActivity(intent)
        }

    }
}