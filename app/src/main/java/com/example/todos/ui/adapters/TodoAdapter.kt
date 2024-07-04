package com.example.todos.ui.adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.todos.R
import com.example.todos.ui.listeners.OnButtonClickListener
import com.example.todos.databinding.TodoListBinding
import com.example.todos.domain.pojo.Todo

class TodoAdapter:RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {
    private var todoList = ArrayList<Todo>()
    private var onButtonClickListener: OnButtonClickListener? = null

    fun setToDoList(todoList:ArrayList<Todo>){
        this.todoList = todoList
        notifyDataSetChanged()
    }

    fun setOnButtonClickListener(listener: OnButtonClickListener) {
        onButtonClickListener = listener
    }

    inner class TodoViewHolder(val binding:TodoListBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(currenttodo: Todo) {
            if(!currenttodo.completed){
                binding.btnDelete.setOnClickListener{
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION){
                        val todo = todoList[position]
                        onButtonClickListener?.onDeleteButtonClicked(position,todo)
                    }
                }

                binding.btnComplete.setOnClickListener{
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION){
                        val todo = todoList[position]
                        onButtonClickListener?.onCompleteButtonClicked(position,todo)
                    }
                }
            } else {
                binding.btnDelete.visibility = View.GONE
                binding.btnComplete.visibility = View.GONE
                binding.tvTask.paintFlags = binding.tvTask.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                binding.tvStatus.setTextColor(ContextCompat.getColor(binding.tvStatus.context, R.color.success))
                binding.tvStatus.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(TodoListBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val currenttodo = todoList[position]
        holder.binding.tvTask.text = currenttodo.todo
        holder.bind(currenttodo)
    }


    override fun getItemCount(): Int {
        return todoList.size
    }
}