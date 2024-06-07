package com.example.todos.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todos.databinding.AllTodoListBinding
import com.example.todos.databinding.TodoListBinding
import com.example.todos.pojo.Todo

class AllTodoAdapter:RecyclerView.Adapter<AllTodoAdapter.AllTodoViewHolder>() {
    private var todoList = ArrayList<Todo>()

    fun setToDoList(todoList:ArrayList<Todo>){
        this.todoList = todoList
        notifyDataSetChanged()
    }
    class AllTodoViewHolder(val binding:AllTodoListBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllTodoViewHolder {
        return AllTodoViewHolder(AllTodoListBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: AllTodoViewHolder, position: Int) {
        val currenttodo = todoList[position]
        holder.binding.tvAllTask.text = currenttodo.todo
        val status : String
        val statuscolor : Int
        if(currenttodo.completed){
            status = "Completed"
            statuscolor = Color.GREEN
        } else {
            status = "Incomplete"
            statuscolor = Color.RED
        }
        holder.binding.taskStatus.setTextColor(statuscolor)
        holder.binding.taskStatus.text = status
    }

    override fun getItemCount(): Int {
        return todoList.size
    }
}