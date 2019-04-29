package com.bluewhale.sa

import com.bluewhale.sa.data.FakeShiftRemoteDataSource
import com.bluewhale.sa.data.source.ShiftDataSource
import com.bluewhale.sa.navigator.BaseSchedulerProvider
import com.bluewhale.sa.navigator.ImmediateSchedulerProvider
import com.bluewhale.sa.network.api.ShiftAPI
import com.example.demo.network.APIRegister
import com.example.demo.network.APIUser
import com.example.demo.network.RegisterRepository
import com.example.demo.network.UserRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import com.bluewhale.sa.util.InjectorInterface

@Module
class Injection : InjectorInterface {
    @Provides
    @Singleton
    override fun provideBaseSchedulerProvider(): BaseSchedulerProvider {
        return ImmediateSchedulerProvider()
    }

    @Provides
    override fun provideShiftDataSource(api: ShiftAPI): ShiftDataSource {
        return FakeShiftRemoteDataSource(api)
    }

    @Provides
    override fun provideUserRepository(navi: BaseSchedulerProvider, api: APIUser): UserRepository {
        return UserRepository(navi, api)
    }

    @Provides
    override fun provideRegisterRepository(navi: BaseSchedulerProvider, api: APIRegister): RegisterRepository {
        return RegisterRepository(navi, api)
    }
}