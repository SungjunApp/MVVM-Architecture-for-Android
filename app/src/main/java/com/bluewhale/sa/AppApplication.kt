package com.bluewhale.sa

import android.app.Application
import com.bluewhale.sa.di.AppComponent
import com.bluewhale.sa.di.AppModule
import com.bluewhale.sa.di.DaggerAppComponent
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.bluewhale.sa.di.RequestMaker

class AppApplication : Application() {

    lateinit var wikiComponent: AppComponent

    override fun onCreate()
    {
        super.onCreate()
        Logger.addLogAdapter(AndroidLogAdapter())
        wikiComponent = initDagger(this)

    }

    private fun initDagger(app: AppApplication): AppComponent =
        DaggerAppComponent.builder()
            .appModule(AppModule(app))
            .build()


}