package com.example.todos.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todos.MainApplication
import com.example.todos.db.Repository
import com.example.todos.viewmodelfactory.TodoViewModelFactory
import com.example.todos.activity.auth.SignInActivity
import com.example.todos.adapters.OnButtonClickListener
import com.example.todos.adapters.TodoAdapter
import com.example.todos.databinding.FragmentMyTaskBinding
import com.example.todos.db.AppDatabase
import com.example.todos.others.RetrofitInstance
import com.example.todos.pojo.Todo
import com.example.todos.viewModels.TodoViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MyTaskFragment : Fragment(), OnButtonClickListener {

    private val TAG = "MyTaskFragment"

    private lateinit var binding: FragmentMyTaskBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var todoViewModel: TodoViewModel
    private val todoAdapter = TodoAdapter()
    private var userId : Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = requireActivity().getSharedPreferences(SignInActivity.PREF_NAME, Context.MODE_PRIVATE)
        userId = sharedPreferences.getInt(SignInActivity.PREF_KEY_USER_ID,-1)
        val db = AppDatabase.getInstance(requireContext())
        val userDao = db.userDao()
        val todoDao = db.todoDao()
        val repository = Repository(todoDao,userDao, RetrofitInstance.userApi)
        val todoViewModelFactory = TodoViewModelFactory(repository, userId)
        todoViewModel = ViewModelProvider(this, todoViewModelFactory)[TodoViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMyTaskBinding.inflate(inflater, container, false)
        val view =  binding.root
        todoAdapter.setOnButtonClickListener(this)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        todoViewModel.getAvailableTodos()
        prepareRecyclerView()
        observeLiveData()
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
                if(todos.isEmpty() && todos != null){
                    MainApplication.showToastMessage("No Todo Available")
                } else {
                    todoAdapter.setToDoList(todoList = todos as ArrayList<Todo>)
                }

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
                    userId = userId)
                lifecycleScope.launch {
                    binding.circularProgress.visibility = View.VISIBLE
                    delay(1000L)
                    binding.circularProgress.visibility = View.GONE
                    todoViewModel.insertTodo(todo)
                    todoViewModel.getAvailableTodos()
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
                todoViewModel.getAvailableTodos()
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
                val completeTodo = Todo(todo.id, todo.todo, completed = true, userId)
                todoViewModel.insertTodo(completeTodo)
            }
            .setNegativeButton("Cancel"){dialog, _ -> dialog.cancel()
            }
            .create()
        dialog.show()
    }
}