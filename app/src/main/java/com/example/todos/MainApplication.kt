package com.example.todos

import android.app.Application
import android.content.Context
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainApplication : Application() {

    companion object {
        private var instance: MainApplication? = null

        private fun applicationContext() : Context {
            return instance!!.applicationContext
        }
        fun showToastMessage(message : String){
            val toast = Toast.makeText(applicationContext(), message, Toast.LENGTH_SHORT)
            toast.show()

            CoroutineScope(Dispatchers.Main).launch {
                delay(1000)
                toast.cancel()
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

    }
}