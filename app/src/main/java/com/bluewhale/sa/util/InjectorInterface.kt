package com.bluewhale.sa.util

import com.bluewhale.sa.data.source.ShiftDataSource
import com.bluewhale.sa.navigator.BaseSchedulerProvider
import com.bluewhale.sa.network.api.ShiftAPI
import com.example.demo.network.*

interface InjectorInterface {
    fun provideBaseSchedulerProvider(): BaseSchedulerProvider
    fun provideShiftDataSource(api: ShiftAPI): ShiftDataSource
    fun provideUserRepository(navi: BaseSchedulerProvider, api: APIUser): UserRepository
    fun provideRegisterRepository(navi: BaseSchedulerProvider, api: APIRegister): RegisterRepository
    fun provideMyAssetRepository(navi: BaseSchedulerProvider, api: APIMyAsset): MyAssetRepository
}