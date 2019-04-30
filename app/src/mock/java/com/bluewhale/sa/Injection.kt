package com.bluewhale.sa

import com.bluewhale.sa.data.FakeMyAssetRepository
import com.bluewhale.sa.data.FakeRegisterRepository
import com.bluewhale.sa.data.FakeShiftRemoteDataSource
import com.bluewhale.sa.data.FakeUserRepository
import com.bluewhale.sa.data.source.ShiftDataSource
import com.bluewhale.sa.navigator.BaseSchedulerProvider
import com.bluewhale.sa.navigator.ImmediateSchedulerProvider
import com.bluewhale.sa.network.api.ShiftAPI
import com.bluewhale.sa.util.InjectorInterface
import com.example.demo.network.APIMyAsset
import com.example.demo.network.APIRegister
import com.example.demo.network.APIUser
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class Injection : InjectorInterface {

    @Provides
    @Singleton
    override fun provideBaseSchedulerProvider(): BaseSchedulerProvider {
        return ImmediateSchedulerProvider()
    }

    @Provides
    override fun provideShiftDataSource(api: ShiftAPI): ShiftDataSource {
        return FakeShiftRemoteDataSource()
    }

    @Provides
    override fun provideUserRepository(navi: BaseSchedulerProvider, api: APIUser): FakeUserRepository {
        return FakeUserRepository()
    }

    @Provides
    override fun provideRegisterRepository(navi: BaseSchedulerProvider, api: APIRegister): FakeRegisterRepository {
        return FakeRegisterRepository()
    }

    @Provides
    override fun provideMyAssetRepository(navi: BaseSchedulerProvider, api: APIMyAsset): FakeMyAssetRepository {
        return FakeMyAssetRepository()
    }
}