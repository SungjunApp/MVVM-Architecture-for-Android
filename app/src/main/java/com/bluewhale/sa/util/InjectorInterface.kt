package com.bluewhale.sa.util

import com.bluewhale.sa.data.source.ShiftDataSource
import com.bluewhale.sa.navigator.BaseSchedulerProvider
import com.bluewhale.sa.network.api.ShiftAPI
import com.example.demo.network.APIRegister
import com.example.demo.network.APIUser
import com.example.demo.network.RegisterRepository
import com.example.demo.network.UserRepository

interface InjectorInterface {
    fun provideBaseSchedulerProvider(): BaseSchedulerProvider
    fun provideShiftDataSource(api: ShiftAPI): ShiftDataSource
    fun provideUserRepository(navi: BaseSchedulerProvider, api: APIUser): UserRepository
    fun provideRegisterRepository(navi: BaseSchedulerProvider, api: APIRegister): RegisterRepository
}