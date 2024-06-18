package com.example.todos.others

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class Utilities() {

    fun showMessage(context:Context, message:String){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}