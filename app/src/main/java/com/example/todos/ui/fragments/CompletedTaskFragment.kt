package com.example.todos.ui.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todos.data.local.AppDatabase
import com.example.todos.data.pojo.Todo
import com.example.todos.databinding.FragmentCompletedTaskBinding
import com.example.todos.ui.adapters.HorizontalCalendarAdapter
import com.example.todos.ui.adapters.TodoAdapter
import com.example.todos.ui.horizontalcalendar.CalendarDateModel
import com.example.todos.ui.listeners.OnClickListener
import com.example.todos.util.helper.SharedPreferenceHelper
import com.example.todos.viewmodels.CalendarViewModel
import com.example.todos.viewmodels.TodoViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date
import javax.inject.Inject


@AndroidEntryPoint
class CompletedTaskFragment : Fragment(), OnClickListener {

    private lateinit var binding: FragmentCompletedTaskBinding
    private lateinit var todoViewModel: TodoViewModel
    private lateinit var calendarViewModel: CalendarViewModel
    private lateinit var horizontalCalendarAdapter : HorizontalCalendarAdapter
    private val todoAdapter = TodoAdapter()

    @Inject
    lateinit var sharedPreferenceHelper: SharedPreferenceHelper
    @Inject
    lateinit var appDatabase: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        todoViewModel = ViewModelProvider(this)[TodoViewModel::class.java]
        calendarViewModel = ViewModelProvider(this)[CalendarViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCompletedTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()
        binding.ivCalendarPrevious.setOnClickListener{
            calendarViewModel.previousMonth()
        }
        binding.ivCalendarNext.setOnClickListener{
            calendarViewModel.nextMonth()
        }

        calendarViewModel.currentMonthYear.observe(viewLifecycleOwner) { monthYear ->
            binding.textDateMonth.text = monthYear
        }

        calendarViewModel.datesInMonth.observe(viewLifecycleOwner){
            dates ->
            horizontalCalendarAdapter = HorizontalCalendarAdapter(dates)
            binding.recyclerView.adapter = horizontalCalendarAdapter
            horizontalCalendarAdapter.setOnClickListener(this)
        }




    }

    private fun prepareRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

        binding.rvAvailableTodos.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = todoAdapter
        }
    }

    override fun onDateClicked(date: Date) {
        todoViewModel.availableTodos.observe(viewLifecycleOwner
        ) { todos ->
            todoAdapter.setToDoList(todoList = todos as ArrayList<Todo>)
        }
    }
}