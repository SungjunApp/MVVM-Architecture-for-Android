package com.bluewhale.sa

import android.app.Activity
import android.app.Application
import com.bluewhale.sa.data.source.ShiftDataSource
import com.bluewhale.sa.data.source.ShiftRepository
import com.bluewhale.sa.data.source.remote.ShiftRemoteDataSource
import com.bluewhale.sa.navigator.BaseNavigator
import com.bluewhale.sa.navigator.BaseSchedulerProvider
import com.bluewhale.sa.navigator.Navigator
import com.bluewhale.sa.navigator.SchedulerProvider
import com.bluewhale.sa.network.api.ShiftAPI
import com.example.demo.network.APIRegister
import com.example.demo.network.APIUser
import com.example.demo.network.RegisterRepository
import com.example.demo.network.UserRepository
import com.bluewhale.sa.util.InjectorInterface
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class Injection :InjectorInterface{
    @Provides
    @Singleton
    override fun provideBaseSchedulerProvider(): BaseSchedulerProvider {
        return SchedulerProvider()
    }

    @Provides
    override fun provideShiftDataSource(api:ShiftAPI): ShiftDataSource {
        return ShiftRemoteDataSource(api)
    }

    @Provides
    override fun provideUserRepository(navi: BaseSchedulerProvider, api:APIUser): UserRepository {
        return UserRepository(navi, api)
    }

    @Provides
    override fun provideRegisterRepository(navi: BaseSchedulerProvider, api:APIRegister): RegisterRepository {
        return RegisterRepository(navi, api)
    }
}