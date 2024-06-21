package com.example.todos.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.todos.util.helper.SharedPreferenceHelper
import com.example.todos.ui.activities.SignInActivity
import com.example.todos.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding

    @Inject
    lateinit var sharedPreferenceHelper: SharedPreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
        sharedPreferenceHelper.clear()
        redirectToLogin()
    }

    private fun redirectToLogin() {
        val intent = Intent(requireActivity(), SignInActivity::class.java)
        startActivity(intent)
    }

}