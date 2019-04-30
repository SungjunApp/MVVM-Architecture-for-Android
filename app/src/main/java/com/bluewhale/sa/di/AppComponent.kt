package com.bluewhale.sa.di

import com.bluewhale.sa.AppApplication
import com.bluewhale.sa.Injection
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        NetworkModule::class,
        APIModule::class,
        ActivityBuildModule::class,
        Injection::class
    ]
)
interface AppComponent {

    @Component.Builder
    interface Builder {

        fun build(): AppComponent

        @BindsInstance
        fun applicationBind(application: AppApplication): Builder
    }

    fun inject(application: AppApplication)
}