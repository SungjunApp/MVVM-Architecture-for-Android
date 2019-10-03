package com.sjsoft.app.network.repository

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

interface PreferenceDataSource{
    fun setCookie(text: String?)
    fun getCookie():String?
}

class PreferenceRepository constructor(val context: Context): PreferenceDataSource{
    val PREF_File = "User"
    val pref:SharedPreferences = context.getSharedPreferences(PREF_File, Activity.MODE_PRIVATE)

    override fun setCookie(text: String?) {
        val editor = pref.edit()
        editor.putString("cookie", text)
        editor.apply()
    }

    override fun getCookie():String? {
        return pref.getString("cookie", null)
    }
}