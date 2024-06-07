package com.example.todos.others

import android.content.Context
import android.widget.Toast

class Utilities() {

    fun showMessage(context:Context, message:String){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}