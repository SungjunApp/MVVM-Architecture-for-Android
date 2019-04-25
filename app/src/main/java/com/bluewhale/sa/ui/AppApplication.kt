package com.bluewhale.sa.ui

import android.app.Application
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.bluewhale.sa.network.RequestMaker

class AppApplication : Application() {
    lateinit var requestMaker: RequestMaker
    override fun onCreate()
    {
        super.onCreate()
        requestMaker = RequestMaker(this)
        Logger.addLogAdapter(AndroidLogAdapter())
    }
}