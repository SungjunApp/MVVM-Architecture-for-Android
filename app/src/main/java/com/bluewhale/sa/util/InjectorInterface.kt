package com.bluewhale.sa.util

import com.bluewhale.sa.model.source.ShiftDataSource
import com.bluewhale.sa.navigator.BaseSchedulerProvider
import com.bluewhale.sa.network.api.*
import com.example.demo.network.MyAssetRepository
import com.example.demo.network.RegisterRepository
import com.example.demo.network.TradeRepository
import com.example.demo.network.UserRepository

interface InjectorInterface {
    fun provideBaseSchedulerProvider(): BaseSchedulerProvider
    fun provideShiftDataSource(api: ShiftAPI): ShiftDataSource
    fun provideUserRepository(navi: BaseSchedulerProvider, api: APIUser): APIUser
    fun provideRegisterRepository(navi: BaseSchedulerProvider, api: APIRegister): APIRegister
    fun provideTradeRepository(navi: BaseSchedulerProvider, api: APITrade): APITrade
    fun provideMyAssetRepository(navi: BaseSchedulerProvider, api: APIMyAsset): APIMyAsset
}