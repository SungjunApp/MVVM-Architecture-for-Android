package com.bluewhale.sa.di

import com.bluewhale.sa.network.api.ShiftAPI
import dagger.Module
import retrofit2.Retrofit
import dagger.Provides
import io.reactivex.Single


//@Module
class APIMaker{
    //@Provides
    fun prodiveShiftAPI(retrofit: Retrofit): ShiftAPI {
        return retrofit
            .create(ShiftAPI::class.java)
    }
}