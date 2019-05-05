package com.sjsoft.app.util

import com.sjsoft.app.model.source.ShiftDataSource
import com.sjsoft.app.navigator.BaseSchedulerProvider
import com.sjsoft.app.network.api.*

interface InjectorInterface {
    fun provideBaseSchedulerProvider(): BaseSchedulerProvider
    fun provideShiftDataSource(api: ShiftAPI): ShiftDataSource
    fun provideUserRepository(navi: BaseSchedulerProvider, api: APIUser): APIUser
    fun provideRegisterRepository(navi: BaseSchedulerProvider, api: APIRegister): APIRegister
    fun provideTradeRepository(navi: BaseSchedulerProvider, api: APITrade): APITrade
    fun provideMyAssetRepository(navi: BaseSchedulerProvider, api: APIMyAsset): APIMyAsset
}