package com.example.digger.utils

import android.text.InputFilter
import android.widget.EditText

object UiExtension {

    fun EditText.acceptNamesOnly() {
        val regex = Regex("[a-zA-Z .]")
        filters = arrayOf(InputFilter { source, _, _, _, _, _ ->
            source.filter { regex.matches(it.toString()) }
        })
    }

}