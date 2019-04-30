package com.bluewhale.sa.util

import com.bluewhale.sa.model.source.ShiftDataSource
import com.bluewhale.sa.navigator.BaseSchedulerProvider
import com.bluewhale.sa.network.api.APITrade
import com.bluewhale.sa.network.api.APIUser
import com.bluewhale.sa.network.api.ShiftAPI

interface InjectorInterface {
    fun provideBaseSchedulerProvider(): BaseSchedulerProvider
    fun provideShiftDataSource(api: ShiftAPI): ShiftDataSource
    fun provideUserRepository(navi: BaseSchedulerProvider, api: APIUser): APIUser
    fun provideRegisterRepository(navi: BaseSchedulerProvider, api: APIRegister): APIRegister
    fun provideTradeRepository(navi: BaseSchedulerProvider, api: APIMyAsset): APITrade
    fun provideMyAssetRepository(navi: BaseSchedulerProvider, api: APIMyAsset): APIMyAsset
}