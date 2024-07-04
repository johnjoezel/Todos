package com.example.todos.ui.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.todos.MainApplication
import com.example.todos.R
import com.example.todos.databinding.ActivitySignUpBinding
import com.example.todos.domain.local.AppDatabase
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
        authViewModel.returnLong.observe(this){long ->
            val intent = Intent(this, SignInActivity::class.java)
            if(long != null){
                lifecycleScope.launch {
                    delay(500L)
                    MainApplication.showToastMessage("Registered Successfully")
                    binding.circularProgress.visibility = View.VISIBLE
                    delay(1500L)
                    binding.circularProgress.visibility = View.GONE
                    startActivity(intent)
                }
            }
        }
        authViewModel.usernameAvailable.observe(this){ isAvailable ->
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

    fun goToSignActivity(view : View){
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
    }
}