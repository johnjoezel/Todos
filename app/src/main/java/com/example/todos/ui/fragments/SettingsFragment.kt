package com.example.todos.ui.fragments

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.todos.data.local.AppDatabase
import com.example.todos.util.helper.SharedPreferenceHelper
import com.example.todos.ui.activities.SignInActivity
import com.example.todos.databinding.FragmentSettingsBinding
import com.example.todos.viewmodels.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    companion object{
        private const val CAMERA_REQUEST = 123
    }


    private lateinit var binding: FragmentSettingsBinding
    private lateinit var userViewModel: UserViewModel
    private var imageUri: Uri? = null

    @Inject
    lateinit var appDatabase: AppDatabase

    @Inject
    lateinit var sharedPreferenceHelper: SharedPreferenceHelper

    private val contract = registerForActivityResult(ActivityResultContracts.TakePicture()){
        binding.ivUserProfImage.setImageURI(null)
        binding.ivUserProfImage.setImageURI(imageUri)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        userViewModel.fetchUser(sharedPreferenceHelper.userId)
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
        checkPermissions()
        binding.ivUserProfImage.setOnClickListener{

            imageUri = storeUri()
            contract.launch(imageUri)
        }
        // Retrieve user ID from SharedPreferences
        binding.tvLogout.setOnClickListener{
            confirmLogout()
        }

        binding.btnSaveProfile.setOnClickListener{
            userViewModel.updateUser(sharedPreferenceHelper.userId, imageUri?.toString())
        }

        userViewModel.user.observe(viewLifecycleOwner){
            user->
            Glide.with(requireContext()).load(user.imgUri).into(binding.ivUserProfImage)
        }

    }

    private fun checkPermissions() {
        if(ContextCompat.checkSelfPermission(requireActivity(),android.Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(requireActivity(),
                arrayOf(android.Manifest.permission.CAMERA),
                CAMERA_REQUEST)
        }
    }

    private fun storeUri(): Uri {
        val image = File(requireContext().filesDir, "${sharedPreferenceHelper.userId}_profPic.png")
        return FileProvider.getUriForFile(requireContext(),
            "com.example.todos.fileProvider",
            image
        )
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
        sharedPreferenceHelper.remove(SharedPreferenceHelper.PREF_KEY_USER_ID)
        sharedPreferenceHelper.remove(SharedPreferenceHelper.PREF_KEY_IS_LOGGED_IN)
        redirectToLogin()
    }

    private fun redirectToLogin() {
        val intent = Intent(requireActivity(), SignInActivity::class.java)
        startActivity(intent)
    }

}