package com.bluewhale.sa

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.bluewhale.sa.di.DaggerAppComponent
import com.facebook.stetho.Stetho
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class AppApplication : Application(), HasActivityInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingAndroidInjector

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG)
            Stetho.initializeWithDefaults(this)

        Logger.addLogAdapter(AndroidLogAdapter())

        DaggerAppComponent
            .builder()
            .applicationBind(this)
            .build()
            .inject(this)
    }
}