package com.bluewhale.sa

import com.bluewhale.sa.navigator.BaseSchedulerProvider
import com.bluewhale.sa.navigator.ImmediateSchedulerProvider
import com.bluewhale.sa.network.api.ShiftAPI
import com.bluewhale.sa.repository.*
import com.bluewhale.sa.model.source.ShiftDataSource
import com.bluewhale.sa.util.InjectorInterface
import com.example.demo.network.APIMyAsset
import com.example.demo.network.APIRegister
import com.bluewhale.sa.network.api.APIUser
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
    override fun provideTradeRepository(navi: BaseSchedulerProvider, api: APIMyAsset): FakeTradeRepository {
        return FakeTradeRepository()
    }

    @Provides
    override fun provideMyAssetRepository(navi: BaseSchedulerProvider, api: APIMyAsset): FakeMyAssetRepository {
        return FakeMyAssetRepository()
    }
}