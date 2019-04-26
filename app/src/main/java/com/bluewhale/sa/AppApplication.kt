package com.bluewhale.sa

import android.app.Activity
import android.app.Application
import com.bluewhale.sa.di.DaggerAppComponent
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class AppApplication : Application() , HasActivityInjector {

    @Inject lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>
    override fun onCreate()
    {
        super.onCreate()
        Logger.addLogAdapter(AndroidLogAdapter())

        DaggerAppComponent
            .builder()
            .applicationBind(this)
            .build()
            .inject(this)
    }


    override fun activityInjector(): AndroidInjector<Activity> = dispatchingAndroidInjector

}