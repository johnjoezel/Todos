package com.example.todos.others

import android.text.TextUtils

class TextUtilities() {

    fun getEmptyInputIndices(vararg inputs: CharSequence?): List<Int> {
        val emptyIndices = mutableListOf<Int>()
        inputs.forEachIndexed { index, input ->
            if (TextUtils.isEmpty(input)) {
                emptyIndices.add(index)
            }
        }
        return emptyIndices
    }
}