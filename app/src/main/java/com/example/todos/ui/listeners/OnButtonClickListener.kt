package com.example.todos.ui.listeners

import com.example.todos.domain.pojo.Todo

interface OnButtonClickListener {
    fun onDeleteButtonClicked(position : Int, todo : Todo)
    fun onCompleteButtonClicked(position : Int, todo : Todo)
}
