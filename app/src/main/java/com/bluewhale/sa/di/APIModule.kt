package com.bluewhale.sa.di

import com.bluewhale.sa.network.api.ShiftAPI
import com.example.demo.network.APIMyAsset
import com.example.demo.network.APIRegister
import com.bluewhale.sa.network.api.APITrade
import com.bluewhale.sa.network.api.APIUser
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit


@Module
class APIModule {
    @Provides
    fun provideShiftAPI(retrofit: Retrofit): ShiftAPI {
        return retrofit.create(ShiftAPI::class.java)
    }

    @Provides
    fun provideAPIUser(retrofit: Retrofit): APIUser {
        return retrofit.create(APIUser::class.java)
    }

    @Provides
    fun provideAPIRegister(retrofit: Retrofit): APIRegister {
        return retrofit.create(APIRegister::class.java)
    }

    @Provides
    fun provideAPITrade(retrofit: Retrofit): APITrade {
        return retrofit.create(APITrade::class.java)
    }

    @Provides
    fun provideAPIMyAsset(retrofit: Retrofit): APIMyAsset {
        return retrofit.create(APIMyAsset::class.java)
    }

}