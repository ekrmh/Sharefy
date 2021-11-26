package com.sharefy.android.utils

import android.content.Context
import android.content.SharedPreferences

object AppPreferences {
    private const val NAME = "Sharefy"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences

    private val EMAIL = Pair("EMAIL", "")
    private val PASSWORD = Pair("PASSWORD", "")

    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    var email: String
        get() = preferences.getString(EMAIL.first, EMAIL.second).toString()
        set(value) = preferences.edit {
            it.putString(EMAIL.first, value)
        }

    var password: String
        get() = preferences.getString(PASSWORD.first, PASSWORD.second).toString()
        set(value) = preferences.edit {
            it.putString(PASSWORD.first, value)
        }
}