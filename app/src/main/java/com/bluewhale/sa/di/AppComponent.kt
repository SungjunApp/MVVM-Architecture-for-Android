package com.bluewhale.sa.di

import com.bluewhale.sa.AppApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivityBuildModule::class,
        FragmentBuildModule::class,
        AppModule::class,
        RequestMaker::class,
        APIMaker::class
        /*RequestMaker::class,
        ViewModule::class*/
        /*AppModule::class, *//*RequestMaker::class, ViewModule::class, APIMaker::class*/]
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