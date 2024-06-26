package com.example.todos.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todos.data.pojo.Todo
import com.example.todos.databinding.RecentTaskItemBinding

class RecentTaskAdapater : RecyclerView.Adapter<RecentTaskAdapater.RecentTaskViewHolder>() {
    private var todoList = ArrayList<Todo>()

    fun setToDoList(todoList:ArrayList<Todo>){
        this.todoList = todoList
        notifyDataSetChanged()
    }


    inner class RecentTaskViewHolder(val binding: RecentTaskItemBinding): RecyclerView.ViewHolder(binding.root){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentTaskViewHolder {
        return RecentTaskViewHolder(RecentTaskItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: RecentTaskViewHolder, position: Int) {
        val currenttodo = todoList[position]
        holder.binding.tvRecentTasks.text = currenttodo.todo
    }


    override fun getItemCount(): Int {
        return todoList.size
    }
}