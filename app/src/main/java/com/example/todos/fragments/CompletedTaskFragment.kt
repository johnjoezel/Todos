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
import com.example.todos.adapters.TodoAdapter
import com.example.todos.databinding.FragmentCompletedTaskBinding
import com.example.todos.databinding.FragmentMyTaskBinding
import com.example.todos.pojo.Todo
import com.example.todos.viewModels.TodoViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [CompletedTaskFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CompletedTaskFragment : Fragment() {

    private lateinit var binding: FragmentCompletedTaskBinding
    private lateinit var todoMVVM: TodoViewModel
    private lateinit var todoAdapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        todoMVVM = ViewModelProvider(this)[TodoViewModel::class.java]
        todoAdapter = TodoAdapter()

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

        // Retrieve user ID from SharedPreferences
        val sharedPreferences = activity?.getSharedPreferences("my_shared_prefs", Context.MODE_PRIVATE)
        val userId = sharedPreferences?.getInt("userId", -1)

        prepareRecyclerView()
        if (userId != null) {
            todoMVVM.getAllCompletedByUserId(userId)
        }
        observerToDoLiveData()

    }

    private fun prepareRecyclerView() {
        binding.rvCompletedtodos.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = todoAdapter
        }
    }

    private fun observerToDoLiveData() {
        todoMVVM.observeToDoLiveData().observe(viewLifecycleOwner
        ) {
                todoList->
            todoAdapter.setToDoList(todoList = todoList as ArrayList<Todo>)
        }

    }

}