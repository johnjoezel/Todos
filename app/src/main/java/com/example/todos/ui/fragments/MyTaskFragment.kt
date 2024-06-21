package com.example.todos.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todos.data.local.AppDatabase
import com.example.todos.data.pojo.Todo
import com.example.todos.databinding.FragmentMyTaskBinding
import com.example.todos.ui.adapters.TodoAdapter
import com.example.todos.ui.listeners.OnButtonClickListener
import com.example.todos.util.helper.SharedPreferenceHelper
import com.example.todos.viewmodels.TodoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MyTaskFragment : Fragment(), OnButtonClickListener {

    private val TAG = "MyTaskFragment"

    private lateinit var binding: FragmentMyTaskBinding
    private val todoAdapter = TodoAdapter()
    private lateinit var todoViewModel: TodoViewModel

    @Inject
    lateinit var sharedPreferenceHelper: SharedPreferenceHelper
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
        binding = FragmentMyTaskBinding.inflate(inflater, container, false)
        todoAdapter.setOnButtonClickListener(this)
        prepareRecyclerView()
        observeLiveData()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.addTask.setOnClickListener{
            onFABclick()
        }

    }

    private fun observeLiveData() {
        lifecycleScope.launch {
            binding.rvTodos.visibility = View.GONE
            delay(1000L)
            binding.circularProgress.visibility = View.GONE
            binding.rvTodos.visibility = View.VISIBLE
            todoViewModel.availableTodos.observe(viewLifecycleOwner
            ) { todos ->
                if(todos.isNotEmpty()){
                    binding.tvNoTodos.visibility = View.GONE

                } else {
                    binding.tvNoTodos.visibility = View.VISIBLE
                }
                todoAdapter.setToDoList(todoList = todos as ArrayList<Todo>)
            }
        }
    }

    private fun prepareRecyclerView() {
        binding.rvTodos.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = todoAdapter
        }
    }

    private fun onFABclick() {
        val inputbox = EditText(context)
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Enter todo")
            .setView(inputbox)
            .setPositiveButton("OK"){_, _->
                val todoDesc = inputbox.text.toString()
                val todo = Todo(completed = false,
                    todo = todoDesc,
                    userId = sharedPreferenceHelper.userId)
                lifecycleScope.launch {
                    binding.circularProgress.visibility = View.VISIBLE
                    delay(1000L)
                    binding.circularProgress.visibility = View.GONE
                    todoViewModel.insertTodo(todo)
                }
            }
            .setNegativeButton("Cancel"){dialog, _ -> dialog.cancel()
            }
            .create()
        dialog.show()
    }

    override fun onDeleteButtonClicked(position: Int, todo : Todo) {
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Are you sure you want to delete?")
            .setPositiveButton("Delete"){_, _->
                todoViewModel.deleteTodo(todo)
            }
            .setNegativeButton("Cancel"){dialog, _ -> dialog.cancel()
            }
            .create()
        dialog.show()
    }

    override fun onCompleteButtonClicked(position: Int, todo: Todo) {
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Please confirm")
            .setPositiveButton("Complete"){_, _->
                val completeTodo = Todo(todo.id, todo.todo, completed = true, sharedPreferenceHelper.userId)
                todoViewModel.insertTodo(completeTodo)
            }
            .setNegativeButton("Cancel"){dialog, _ -> dialog.cancel()
            }
            .create()
        dialog.show()
    }
}