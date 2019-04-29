package com.bluewhale.sa.di

import com.bluewhale.sa.network.api.ShiftAPI
import com.example.demo.network.APIRegister
import com.example.demo.network.APIUser
import dagger.Module
import retrofit2.Retrofit
import dagger.Provides
import io.reactivex.Single


@Module
class APIModule{
    @Provides
    fun provideShiftAPI(retrofit: Retrofit): ShiftAPI {
        return retrofit.create(ShiftAPI::class.java)
    }

    @Provides
    fun provideAPIUser(retrofit: Retrofit): APIUser {
        return retrofit.create(APIUser::class.java)
    }

    @Provides
    fun provideAPIRegister(retrofit: Retrofit): APIRegister{
        return retrofit.create(APIRegister::class.java)
    }
}