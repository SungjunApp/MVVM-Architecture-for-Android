package com.bluewhale.sa.network.repository

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.bluewhale.sa.constant.DomainInfo

class UserPreferenceRepository constructor(val context: Context){
    val PREF_File = "User"
    val pref:SharedPreferences = context.getSharedPreferences(PREF_File, Activity.MODE_PRIVATE)

    fun setCookie(text: String?) {
        val editor = pref.edit()
        editor.putString("cookie", text)
        editor.apply()
    }

    fun getCookie():String? {
        return DomainInfo.CLIENT_SHASUM
        //return pref.getString("cookie", null)
    }
}