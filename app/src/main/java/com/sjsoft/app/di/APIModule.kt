package com.sjsoft.app.di

import com.sjsoft.app.data.api.*
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit


@Module
class APIModule {
    @Provides
    fun provideAPIUser(retrofit: Retrofit): LottoAPI {
        return retrofit.create(LottoAPI::class.java)
    }


}