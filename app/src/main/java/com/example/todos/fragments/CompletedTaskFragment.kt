package com.example.todos.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todos.db.Repository
import com.example.todos.viewmodelfactory.TodoViewModelFactory
import com.example.todos.activity.auth.SignInActivity
import com.example.todos.adapters.TodoAdapter
import com.example.todos.databinding.FragmentCompletedTaskBinding
import com.example.todos.db.AppDatabase
import com.example.todos.others.RetrofitInstance
import com.example.todos.pojo.Todo
import com.example.todos.viewModels.TodoViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [CompletedTaskFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CompletedTaskFragment : Fragment() {

    private lateinit var binding: FragmentCompletedTaskBinding
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
        todoViewModel.completedTodos.observe(viewLifecycleOwner
        ) { todos ->
            todoAdapter.setToDoList(todoList = todos as ArrayList<Todo>)
        }

    }

}