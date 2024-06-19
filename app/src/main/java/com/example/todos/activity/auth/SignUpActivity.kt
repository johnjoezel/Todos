package com.example.todos.activity.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.todos.MainApplication
import com.example.todos.R
import com.example.todos.db.AuthRepository
import com.example.todos.viewmodelfactory.AuthViewModelFactory
import com.example.todos.databinding.ActivitySignUpBinding
import com.example.todos.db.AppDatabase
import com.example.todos.others.TextUtilities
import com.example.todos.pojo.Todo
import com.example.todos.pojo.User
import com.example.todos.viewModels.AuthViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private val textUtilities = TextUtilities()
    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = AppDatabase.getInstance(applicationContext)
        val userDao = db.userDao()
        val repository = AuthRepository(userDao)
        val viewModelFactory = AuthViewModelFactory(repository)
        authViewModel = ViewModelProvider(this, viewModelFactory)[AuthViewModel::class.java]

        binding.btnSignup.setOnClickListener{
            verifyUserData()
        }

        binding.tvSignin.setOnClickListener{
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }

        authViewModel.returnLong.observe(this){long ->
            if(long != null){
                val intent = Intent(this, SignInActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun verifyUserData() {
        val firstname = binding.etFirstname.text.toString()
        val lastname = binding.etLastname.text.toString()
        val gender = binding.etGender.text.toString()
        val email = binding.etEmail.text.toString()
        val username = binding.etUsername.text.toString()
        val password = binding.etPassword.text.toString()
        val cpassword = binding.etCpassword.text.toString()
        val fields = listOf(
            Pair(binding.etFirstname, binding.layoutfirstname to R.string.requireFirstname),
            Pair(binding.etLastname, binding.layoutlastname to R.string.requireLastname),
            Pair(binding.etGender, binding.layoutgender to R.string.requireGender),
            Pair(binding.etEmail, binding.layoutemail to R.string.requireEmail),
            Pair(binding.etUsername, binding.layoutusername to R.string.requiredUsername),
            Pair(binding.etPassword, binding.layoutpassword to R.string.requiredPassword),
            Pair(binding.etCpassword, binding.layoutcpassword to R.string.requireCPassword)
        )


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
            authViewModel.checkUsernameAvailability(username)
            authViewModel.usernameAvailable.observe(this){usernameAvailable ->
                if(password == cpassword){
                    binding.layoutcpassword.error = null
                    binding.layoutpassword.error = null
                    if(usernameAvailable){
                        val user = User(
                            firstName = firstname,
                            lastName = lastname,
                            gender = gender,
                            email = email,
                            username = username,
                            password = password)
                        lifecycleScope.launch {
                            delay(500L)
                            MainApplication.showToastMessage("Register Successfully")
                            binding.circularProgress.visibility = View.VISIBLE
                            delay(1500L)
                            binding.circularProgress.visibility = View.GONE
                            authViewModel.signUp(user)
                        }
                    } else {
                        binding.layoutusername.error = getString(R.string.usernameTaken)
                    }
                } else {
                    binding.layoutcpassword.error = getString(R.string.passwordMismatch)
                    binding.layoutpassword.error = getString(R.string.passwordMismatch)
                }
            }
        }
    }
}