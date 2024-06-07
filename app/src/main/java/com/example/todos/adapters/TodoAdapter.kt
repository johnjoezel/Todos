package com.example.todos.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todos.databinding.TodoListBinding
import com.example.todos.pojo.Todo

class TodoAdapter:RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {
    private var todoList = ArrayList<Todo>()

    fun setToDoList(todoList:ArrayList<Todo>){
        this.todoList = todoList
        notifyDataSetChanged()
    }
    class TodoViewHolder(val binding:TodoListBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(TodoListBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val currenttodo = todoList[position]
        holder.binding.tvTask.text = currenttodo.todo
    }

    override fun getItemCount(): Int {
        return todoList.size
    }
}