package com.example.todos.adapters

import com.example.todos.pojo.Todo

interface OnButtonClickListener {
    fun onDeleteButtonClicked(position : Int, todo : Todo)
    fun onCompleteButtonClicked(position : Int, todo : Todo)
}
