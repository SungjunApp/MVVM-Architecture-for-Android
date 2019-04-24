package com.bluewhale.sa.ui.register

import android.annotation.SuppressLint
import android.app.Application
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bluewhale.sa.Injection
import com.bluewhale.sa.data.source.register.RegisterInfoRepository

class RegisterInfoViewModelFactory private constructor(
    private val registerInfoRepository: RegisterInfoRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(RegisterInfoViewModel::class.java) ->
                    RegisterInfoViewModel(registerInfoRepository)
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T

    companion object {

        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: RegisterInfoViewModelFactory? = null

        fun getInstance(application: Application) =
            INSTANCE ?: synchronized(RegisterInfoViewModelFactory::class.java) {
                INSTANCE ?: RegisterInfoViewModelFactory(
                    Injection.provideRegisterRepository(application)
                )
                    .also { INSTANCE = it }
            }


        @VisibleForTesting
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}