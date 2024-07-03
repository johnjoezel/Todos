package com.example.todos.ui.activities

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Text
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.todos.MainApplication
import com.example.todos.R
import com.example.todos.databinding.ActivitySignUpBinding
import com.example.todos.data.local.AppDatabase
import com.example.todos.data.pojo.User
import com.example.todos.viewmodels.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var authViewModel: AuthViewModel

    @Inject
    lateinit var appDatabase: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)

        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]
        binding.authViewModel = authViewModel
        binding.lifecycleOwner = this


        binding.btnSignup.setOnClickListener{
//            verifyUserData()
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

        authViewModel.usernameAvailable.observe(this){ isAvailable ->
            binding.btnSignup.isEnabled = isAvailable
            if(!isAvailable){
                binding.layoutusername.error = getString(R.string.usernameTaken)
            } else {
                binding.layoutusername.error = null
            }
        }


        binding.etUsername.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                authViewModel.checkUsernameAvailability(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

    }

//    private fun verifyUserData() {
//        val email = binding.etEmail.text.toString()
//        val username = binding.etUsername.text.toString()
//        val password = binding.etPassword.text.toString()
//        val fields = listOf(
//            Pair(binding.etEmail, binding.layoutemail to R.string.requireEmail),
//            Pair(binding.etUsername, binding.layoutusername to R.string.requiredUsername),
//            Pair(binding.etPassword, binding.layoutpassword to R.string.requiredPassword),)
//
//
//        var allFieldsFilled = true
//        fields.forEach{(editText, pair) ->
//            val (textInputLayout, errorMessageRedId) = pair
//            if(editText.text!!.isEmpty()){
//                textInputLayout.error = getString(errorMessageRedId)
//                allFieldsFilled = false
//            } else {
//                textInputLayout.error = null
//            }
//        }
//
//        if(allFieldsFilled){
//            authViewModel.checkUsernameAvailability(username)
//            authViewModel.usernameAvailable.observe(this){usernameAvailable ->
//                if(usernameAvailable){
//                    val user = User(
//                        username = username,
//                        password = password)
//                    lifecycleScope.launch {
//                        delay(500L)
//                        MainApplication.showToastMessage("Register Successfully")
//                        binding.circularProgress.visibility = View.VISIBLE
//                        delay(1500L)
//                        binding.circularProgress.visibility = View.GONE
//                        authViewModel.signUp(user)
//                    }
//                } else {
//                    binding.layoutusername.error = getString(R.string.usernameTaken)
//                }
//            }
//        }
//    }
}