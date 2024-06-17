package com.example.todos.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.todos.activity.auth.SignInActivity
import com.example.todos.adapters.AllTodoAdapter
import com.example.todos.adapters.TodoAdapter
import com.example.todos.databinding.FragmentMyTaskBinding
import com.example.todos.databinding.FragmentSettingsBinding
import com.example.todos.viewModels.TodoViewModel


class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    private lateinit var sharedPreferences: SharedPreferences
    private var userId : Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = requireActivity().getSharedPreferences(SignInActivity.PREF_NAME, Context.MODE_PRIVATE)
        userId = sharedPreferences.getInt(SignInActivity.PREF_KEY_USER_ID,-1)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve user ID from SharedPreferences

        binding.tvLogout.setOnClickListener{
            confirmLogout()
        }

    }


    private fun confirmLogout() {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setTitle("Logout")
        alertDialogBuilder.setMessage("Are you sure you want to logout?")
        alertDialogBuilder.setPositiveButton("Yes"){ _,_ ->
            logout()
        }
        alertDialogBuilder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
    private fun logout() {
        val sharedPreferences = requireActivity().getSharedPreferences(SignInActivity.PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean(SignInActivity.PREF_KEY_IS_LOGGED_IN, false)
        editor.remove(SignInActivity.PREF_KEY_USER_ID)
        editor.apply()
        redirectToLogin()
    }

    private fun redirectToLogin() {
        val intent = Intent(requireActivity(), SignInActivity::class.java)
        startActivity(intent)
    }

}