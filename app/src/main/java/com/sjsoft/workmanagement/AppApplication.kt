package com.sjsoft.workmanagement

import android.app.Application
import android.content.Context
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.sjsoft.workmanagement.network.RequestMaker

class AppApplication : Application() {
    lateinit var requestMaker: RequestMaker
    override fun onCreate()
    {
        super.onCreate()
        requestMaker = RequestMaker(this)
        Logger.addLogAdapter(AndroidLogAdapter())
    }
}