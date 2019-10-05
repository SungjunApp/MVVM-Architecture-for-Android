package com.sjsoft.app.data.repository

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

interface PreferenceDataSource{
    fun setReadWelcome(value: Boolean)
    fun getReadWelcome():Boolean
}

class PreferenceRepository constructor(val context: Context): PreferenceDataSource{
    val pref:SharedPreferences = context.getSharedPreferences(FileName, Activity.MODE_PRIVATE)

    override fun setReadWelcome(value: Boolean) {
        val editor = pref.edit()
        editor.putBoolean(readWelcome, value)
        editor.apply()
    }

    override fun getReadWelcome():Boolean {
        return pref.getBoolean(readWelcome, false)
    }

    companion object{
        const val FileName = "User"
        const val readWelcome = "readWelcome"
    }
}