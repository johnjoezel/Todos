package com.example.todos.ui.fragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todos.R
import com.example.todos.data.local.AppDatabase
import com.example.todos.databinding.FragmentCompletedTaskBinding
import com.example.todos.ui.adapters.HorizontalCalendarAdapter
import com.example.todos.ui.horizontalcalendar.HorizontalCalendarSetUp
import com.example.todos.viewmodels.TodoViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject


@AndroidEntryPoint
class CompletedTaskFragment : Fragment(), HorizontalCalendarAdapter.OnItemClickListener{

    private lateinit var binding: FragmentCompletedTaskBinding
    private lateinit var todoViewModel: TodoViewModel

    @Inject
    lateinit var appDatabase: AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        todoViewModel = ViewModelProvider(this)[TodoViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCompletedTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRecyclerView()
        val calendarSetup = HorizontalCalendarSetUp()
        val tvMonth = calendarSetup.setUpCalendarAdapter(binding.recyclerView, this)
        binding.textDateMonth.text = tvMonth

        calendarSetup.setUpCalendarPrevNextClickListener(binding.ivCalendarNext,binding.ivCalendarPrevious, this){
            binding.textDateMonth.text = it
        }

    }

    private fun prepareRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    override fun onItemClick(position : Int) {
        binding.recyclerView.smoothScrollToPosition(26)
        Log.d("imhere", "onItemClick: ${position}")
    }

    // Method to simulate item click programmatically
}