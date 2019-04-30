package com.bluewhale.sa

import com.bluewhale.sa.model.remote.ShiftRemoteDataSource
import com.bluewhale.sa.model.source.ShiftDataSource
import com.bluewhale.sa.navigator.BaseSchedulerProvider
import com.bluewhale.sa.navigator.SchedulerProvider
import com.bluewhale.sa.network.api.*
import com.bluewhale.sa.util.InjectorInterface
import com.example.demo.network.MyAssetRepository
import com.example.demo.network.RegisterRepository
import com.example.demo.network.TradeRepository
import com.example.demo.network.UserRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class Injection : InjectorInterface {

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
    override fun provideUserRepository(navi: BaseSchedulerProvider, api: APIUser): UserRepository {
        return UserRepository(navi, api)
    }

    @Provides
    override fun provideRegisterRepository(navi: BaseSchedulerProvider, api: APIRegister): RegisterRepository {
        return RegisterRepository(navi, api)
    }

    @Provides
    override fun provideMyAssetRepository(navi: BaseSchedulerProvider, api: APIMyAsset): MyAssetRepository {
        return MyAssetRepository(navi, api)
    }

    @Provides
    override fun provideTradeRepository(navi: BaseSchedulerProvider, api: APITrade): TradeRepository {
        return TradeRepository(navi, api)
    }

}
