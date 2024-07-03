package com.example.todos

import android.text.TextUtils
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todos.viewmodels.AuthViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("validateOnFocusChange")
fun TextInputEditText.setOnFocusChangeListener(layout: TextInputLayout) {
    val layoutTag = layout.tag as? String
    this.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
        if(layoutTag != "username"){
            if (!hasFocus) {
                validateField(layout, this)
            } else {
                // Clear error on focus
                layout.error = null
            }
        }
    }
}

private fun validateField(layout: TextInputLayout, editText: TextInputEditText) {
    val text = editText.text?.toString()?.trim()
    if (TextUtils.isEmpty(text)) {
        layout.error = "This field is required"
    } else {
        layout.error = null
    }
    if(layout.id.toString() == "layoutusername"){

    }
}