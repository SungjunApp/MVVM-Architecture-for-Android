package com.sjsoft.app.di

import android.app.Application
import android.content.Context
import com.sjsoft.app.AppApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivityBuildModule::class,
        AppModule::class,
        APIModule::class,
        NetworkModule::class,
        RepositoryModule::class,
        RoomModule::class,
        PixleeModule::class
    ]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(githubApp: AppApplication)

}
