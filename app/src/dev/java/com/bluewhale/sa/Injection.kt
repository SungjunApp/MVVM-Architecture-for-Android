package com.bluewhale.sa

import com.bluewhale.sa.data.source.ShiftDataSource
import com.bluewhale.sa.data.source.remote.ShiftRemoteDataSource
import com.bluewhale.sa.navigator.BaseSchedulerProvider
import com.bluewhale.sa.navigator.SchedulerProvider
import com.bluewhale.sa.network.api.ShiftAPI
import com.example.demo.network.APIRegister
import com.example.demo.network.APIUser
import com.example.demo.network.RegisterRepository
import com.example.demo.network.UserRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class Injection {
    @Provides
    @Singleton
    fun provideBaseSchedulerProvider(): BaseSchedulerProvider {
        return SchedulerProvider()
    }

    @Provides
    fun provideShiftDataSource(api:ShiftAPI): ShiftDataSource {
        return ShiftRemoteDataSource(api)
    }

    @Provides
    fun provideUserRepository(navi: BaseSchedulerProvider, api:APIUser): UserRepository {
        return UserRepository(navi, api)
    }

    @Provides
    fun provideRegisterRepository(navi: BaseSchedulerProvider, api:APIRegister): RegisterRepository {
        return RegisterRepository(navi, api)
    }
}