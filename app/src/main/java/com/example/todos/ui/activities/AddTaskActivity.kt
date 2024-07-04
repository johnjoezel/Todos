package com.example.todos.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.todos.domain.local.AppDatabase
import com.example.todos.domain.pojo.Todo
import com.example.todos.databinding.ActivityAddTaskBinding
import com.example.todos.util.helper.SharedPreferenceHelper
import com.example.todos.viewmodels.TodoViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class AddTaskActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAddTaskBinding
    private lateinit var todoViewModel : TodoViewModel
    @Inject
    lateinit var sharedPreferenceHelper: SharedPreferenceHelper
    @Inject
    lateinit var appDatabase: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        todoViewModel = ViewModelProvider(this)[TodoViewModel::class.java]

        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAddTask.setOnClickListener{
            addTask()
        }

        binding.btnBack.setOnClickListener{
            onBackPressed()
        }

        binding.etTaskDate.setOnClickListener{
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, 0) // Set to midnight for the current day
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            val todayInMillis = calendar.timeInMillis
            val datePicker =
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select date")
                    .setSelection(todayInMillis)
                    .build()

            datePicker.addOnPositiveButtonClickListener { selection ->
                val selectedDate = Date(selection)

                val formattedDate = SimpleDateFormat("MM-dd-yyyy", Locale.getDefault()).format(selectedDate)
                binding.etTaskDate.setText(formattedDate)
            }
            datePicker.show(supportFragmentManager, datePicker.toString())
        }
    }

    private fun addTask() {
        val todoTitle = binding.etTaskTitle.text.toString()
        val todo = Todo(completed = false,
            todo = todoTitle,
            userId = sharedPreferenceHelper.userId)
        lifecycleScope.launch {
            todoViewModel.insertTodo(todo)
            finish()
        }
    }
}