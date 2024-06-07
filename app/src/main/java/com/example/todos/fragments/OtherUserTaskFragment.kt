package com.example.todos.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todos.R
import com.example.todos.adapters.AllTodoAdapter
import com.example.todos.adapters.TodoAdapter
import com.example.todos.databinding.FragmentMyTaskBinding
import com.example.todos.databinding.FragmentOtherUserTaskBinding
import com.example.todos.pojo.Todo
import com.example.todos.viewModels.TodoViewModel


class OtherUserTaskFragment : Fragment() {

    private lateinit var binding:FragmentOtherUserTaskBinding
    private lateinit var todoMVVM: TodoViewModel
    private lateinit var alltodoAdapter: AllTodoAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        todoMVVM = ViewModelProvider(this)[TodoViewModel::class.java]
        alltodoAdapter = AllTodoAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOtherUserTaskBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve user ID from SharedPreferences
        val sharedPreferences = activity?.getSharedPreferences("my_shared_prefs", Context.MODE_PRIVATE)
        val userId = sharedPreferences?.getInt("userId", -1)

        prepareRecyclerView()
        if (userId != null) {
            todoMVVM.getAllTodo()
        }
        observerToDoLiveData()

    }

    private fun prepareRecyclerView() {
        binding.rvAllTodos.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = alltodoAdapter
        }
    }

    private fun observerToDoLiveData() {
        todoMVVM.observeToDoLiveData().observe(viewLifecycleOwner
        ) {
                todoList->
            alltodoAdapter.setToDoList(todoList = todoList as ArrayList<Todo>)
        }

    }

}