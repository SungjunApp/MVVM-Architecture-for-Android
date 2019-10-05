package com.sjsoft.app.data.repository

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

interface PreferenceDataSource{
    fun setReadWelcome(value: Boolean)
    fun getReadWelcome():Boolean
    fun setReservedDrwNo(value: String)
    fun getReservedDrwNo():String?
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

    override fun setReservedDrwNo(value: String) {
        val editor = pref.edit()
        editor.putString(reservedDrwNo, value)
        editor.apply()
    }

    override fun getReservedDrwNo():String? {
        return pref.getString(reservedDrwNo, null)
    }



    companion object{
        const val FileName = "User"
        const val readWelcome = "readWelcome"
        const val reservedDrwNo = "reservedDrwNo" //딥링크를 통해 예약된 조회할 회차번호
    }
}