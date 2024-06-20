package com.example.todos.activity.auth


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.todos.db.AuthRepository
import com.example.todos.viewmodelfactory.AuthViewModelFactory
import com.example.todos.MainActivity
import com.example.todos.MainApplication
import com.example.todos.R
import com.example.todos.databinding.ActivitySignInBinding
import com.example.todos.db.AppDatabase
import com.example.todos.db.Repository
import com.example.todos.others.RetrofitInstance
import com.example.todos.viewModels.AuthViewModel
import com.example.todos.viewModels.TodoViewModel
import com.example.todos.viewmodelfactory.TodoViewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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

        //check if user is already logged in
        if(isLoggedin()){
            redirectToMain()
        }

        setContentView(binding.root)
        val onBackPressedCallback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                showExitConfirmationDialog()
            }
        }
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
        val db = AppDatabase.getInstance(applicationContext)
        val userDao = db.userDao()
        val todoDao = db.todoDao()
        val repository = AuthRepository(userDao)
        val todoRepository = Repository(todoDao,userDao, RetrofitInstance.userApi)
        val viewModelFactory = AuthViewModelFactory(repository)
        authViewModel = ViewModelProvider(this, viewModelFactory)[AuthViewModel::class.java]

        authViewModel.loginUser.observe(this){ user ->
            if(user!=null){
                val userId = user.userId
                saveLoginStatus(true, userId)
                val todoViewModelFactory = TodoViewModelFactory(todoRepository,userId)
                val todoViewModel = ViewModelProvider(this, todoViewModelFactory)[TodoViewModel::class.java]
                todoViewModel.fetchTodoFromApiOneTime()
                lifecycleScope.launch {
                    delay(500L)
                    MainApplication.showToastMessage("Signing In")
                    binding.circularProgress.visibility = View.VISIBLE
                    delay(1500L)
                    binding.circularProgress.visibility = View.GONE
                    redirectToMain()
                }
            } else {
                binding.layoutusername.error = getString(R.string.invalidCredentials)
                binding.layoutpassword.error = getString(R.string.invalidCredentials)
                MainApplication.showToastMessage(getString(R.string.invalidCredentials))
            }
        }

        //when button signin is clicked
        binding.btnSignin.setOnClickListener{
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()
            val fields = listOf(Pair(binding.etUsername, binding.layoutusername to R.string.requiredUsername),
                Pair(binding.etPassword, binding.layoutpassword to R.string.requiredPassword))
            var allFieldsFilled = true
            fields.forEach{(editText, pair) ->
                val (textInputLayout, errorMessageRedId) = pair
                if(editText.text!!.isEmpty()){
                    textInputLayout.error = getString(errorMessageRedId)
                    allFieldsFilled = false
                } else {
                    textInputLayout.error = null
                }
            }
            if(allFieldsFilled){
                authViewModel.signIn(username, password)
            }
        }

        binding.tvSignup.setOnClickListener{
            redirectToSignUp()
        }
    }

    private fun isLoggedin(): Boolean {
        return getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).getBoolean(PREF_KEY_IS_LOGGED_IN, false)
    }

    private fun showExitConfirmationDialog() {
        AlertDialog.Builder(this)
            .setMessage("Are you sure you want to exit?")
            .setPositiveButton("Yes") { _, _ ->
                // Exit the app
                finishAffinity()
                System.exit(0)
            }
            .setNegativeButton("No") { dialog, _ ->
                // Dismiss the dialog
                dialog.dismiss()
            }
            .show()
    }

    private fun redirectToSignUp() {
        val intent = Intent(this, SignUpActivity::class.java)
        // Clear the navigation event to avoid navigating multiple times
        startActivity(intent)
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