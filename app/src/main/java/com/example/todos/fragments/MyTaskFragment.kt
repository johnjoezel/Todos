package com.example.todos.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todos.R
import com.example.todos.activity.auth.SignInActivity
import com.example.todos.adapters.TodoAdapter
import com.example.todos.databinding.FragmentMyTaskBinding
import com.example.todos.others.SwipeToDeleteHelper
import com.example.todos.pojo.Todo
import com.example.todos.viewModels.TodoViewModel


class MyTaskFragment : Fragment() {

    private lateinit var binding: FragmentMyTaskBinding
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
        binding = FragmentMyTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve user ID from SharedPreferences
        val sharedPreferences = activity?.getSharedPreferences("my_shared_prefs", Context.MODE_PRIVATE)
        val userId = sharedPreferences?.getInt("userId", -1)

        prepareRecyclerView()
        if (userId != null) {
            todoMVVM.getAllIncompleteByUserId(userId)
        }
        observerToDoLiveData()
        onFABclick()
        swipeToDelete()


        binding.tvLogout.setOnClickListener{
            confirmLogout()
        }
    }

    private fun confirmLogout() {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setTitle("Logout")
        alertDialogBuilder.setMessage("Are you sure you want to logout?")
        alertDialogBuilder.setPositiveButton("Yes"){ _,_ ->
            logout()
        }
        alertDialogBuilder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun logout() {
        val sharedPreferences = requireActivity().getSharedPreferences(SignInActivity.PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean(SignInActivity.PREF_KEY_IS_LOGGED_IN, false)
        editor.remove(SignInActivity.PREF_KEY_USER_ID)
        editor.apply()

        redirectToLogin()
    }

    private fun redirectToLogin() {
        val intent = Intent(requireActivity(), SignInActivity::class.java)
        startActivity(intent)
    }

    private fun swipeToDelete() {
        val swipeToDeleteHelper = ItemTouchHelper(SwipeToDeleteHelper(todoMVVM,requireContext()))
        swipeToDeleteHelper.attachToRecyclerView(binding.rvTodos)
    }

    private fun onFABclick() {
        binding.addTask.setOnClickListener{
            val inputbox = EditText(context)
            val dialog = AlertDialog.Builder(requireContext())
                .setTitle("Enter todo")
                .setView(inputbox)
                .setPositiveButton("OK"){_, _->
                    val todoDesc = inputbox.text.toString()
                    val todo = Todo(false,1000,todoDesc,5)
                    todoMVVM.addToDo(todo)
                }
                .setNegativeButton("Cancel"){dialog, _ -> dialog.cancel()
                }
                .create()

            dialog.show()
        }
    }

    private fun prepareRecyclerView() {
        binding.rvTodos.apply {
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