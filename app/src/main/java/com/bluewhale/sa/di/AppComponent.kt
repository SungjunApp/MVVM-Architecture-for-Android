package com.bluewhale.sa.di

import com.bluewhale.sa.ViewModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, RequestMaker::class, ViewModule::class])
interface AppComponent{
//    @Component.Builder
//    interface Builder {
//        @BindsInstance
//        fun application(app: App): Builder
//
//        fun build(): AppComponent
//    }
//
//    abstract fun inject(app: App)
}