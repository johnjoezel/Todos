package com.example.todos.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.todos.R
import com.example.todos.databinding.FragmentMyTaskBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyTaskFragment : Fragment() {

    private lateinit var binding : FragmentMyTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMyTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

}