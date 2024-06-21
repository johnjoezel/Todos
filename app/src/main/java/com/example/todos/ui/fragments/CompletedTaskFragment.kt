package com.example.todos.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todos.ui.adapters.TodoAdapter
import com.example.todos.databinding.FragmentCompletedTaskBinding
import com.example.todos.data.local.AppDatabase
import com.example.todos.data.pojo.Todo
import com.example.todos.viewmodels.TodoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 * Use the [CompletedTaskFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class CompletedTaskFragment : Fragment() {

    private lateinit var binding: FragmentCompletedTaskBinding
    private val todoAdapter = TodoAdapter()

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRecyclerView()
        observerToDoLiveData()
    }

    private fun prepareRecyclerView() {
        binding.rvCompletedtodos.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = todoAdapter
        }
    }
    private fun observerToDoLiveData() {
        lifecycleScope.launch {
            delay(1000L)
            binding.circularProgress.visibility = View.GONE
            todoViewModel.completedTodos.observe(viewLifecycleOwner
            ) { todos ->
                if(todos.isNotEmpty()){
                    todoAdapter.setToDoList(todoList = todos as ArrayList<Todo>)
                }
            }
        }
    }
}