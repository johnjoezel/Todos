package com.example.todos.ui.activities

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.todos.R
import com.example.todos.data.local.AppDatabase
import com.example.todos.data.pojo.Todo
import com.example.todos.databinding.ActivityAddTaskBinding
import com.example.todos.util.helper.SharedPreferenceHelper
import com.example.todos.viewmodels.TodoViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
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

        binding.layoutTaskDate.setOnClickListener{
            val datePicker =
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select date")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build()

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