package com.sjsoft.app

import com.sjsoft.app.model.source.ShiftDataSource
import com.sjsoft.app.navigator.BaseSchedulerProvider
import com.sjsoft.app.navigator.ImmediateSchedulerProvider
import com.sjsoft.app.network.api.*
import com.sjsoft.app.repository.*
import com.sjsoft.app.util.InjectorInterface
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
    override fun provideTradeRepository(navi: BaseSchedulerProvider, api: APITrade): FakeTradeRepository {
        return FakeTradeRepository()
    }

    @Provides
    override fun provideMyAssetRepository(navi: BaseSchedulerProvider, api: APIMyAsset): FakeMyAssetRepository {
        return FakeMyAssetRepository()
    }
}