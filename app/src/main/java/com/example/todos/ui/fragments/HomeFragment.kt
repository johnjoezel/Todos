package com.example.todos.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todos.data.local.AppDatabase
import com.example.todos.data.pojo.Todo
import com.example.todos.databinding.FragmentHomeBinding
import com.example.todos.databinding.FragmentMyTaskBinding
import com.example.todos.ui.adapters.RecentTaskAdapater
import com.example.todos.ui.listeners.OnButtonClickListener
import com.example.todos.util.helper.SharedPreferenceHelper
import com.example.todos.viewmodels.TodoViewModel
import com.example.todos.viewmodels.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(), OnButtonClickListener {

    private val TAG = "MyTaskFragment"

    private lateinit var binding: FragmentHomeBinding
    private val recentTaskAdapater = RecentTaskAdapater()
    private lateinit var todoViewModel: TodoViewModel
    private lateinit var userViewModel: UserViewModel

    @Inject
    lateinit var sharedPreferenceHelper: SharedPreferenceHelper
    @Inject
    lateinit var appDatabase: AppDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        todoViewModel = ViewModelProvider(this)[TodoViewModel::class.java]
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        userViewModel.user.observe(viewLifecycleOwner){
            user->
            if(user!=null){
                binding.tvUser.setText("Hi, ${user.username}")
                todoViewModel.availableTodos.observe(viewLifecycleOwner){
                    todos->
                    if(todos.isNotEmpty()) {
                        binding.tvCountTasks.setText(todos.size.toString() + " Tasks")
                    } else {
                        binding.tvCountTasks.setText("0 Tasks" )
                    }
                }
            }
        }
        userViewModel.fetchUser(sharedPreferenceHelper.userId)
        prepareRecyclerView()
        observeLiveData()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


//        binding.addTask.setOnClickListener{
//            onFABclick()
//        }

    }

    private fun observeLiveData() {
        lifecycleScope.launch {
            binding.rvRecentTasks.visibility = View.GONE
            delay(1000L)
            binding.circularProgress.visibility = View.GONE
            binding.rvRecentTasks.visibility = View.VISIBLE
            todoViewModel.availableTodos.observe(viewLifecycleOwner
            ) { todos ->
                recentTaskAdapater.setToDoList(todoList = todos as ArrayList<Todo>)
            }
        }
    }

    private fun prepareRecyclerView() {
        binding.rvRecentTasks.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recentTaskAdapater
        }
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