package com.sjsoft.app.di

import com.sjsoft.app.network.api.*
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit


@Module
class APIModule {
    @Provides
    fun provideAPIUser(retrofit: Retrofit): APIUser {
        return retrofit.create(APIUser::class.java)
    }


}