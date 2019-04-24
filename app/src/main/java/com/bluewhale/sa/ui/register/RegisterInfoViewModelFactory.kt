package com.bluewhale.sa.ui.register

import android.annotation.SuppressLint
import android.app.Application
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bluewhale.sa.Injection
import com.bluewhale.sa.MainActivity
import com.bluewhale.sa.data.source.register.RegisterInfoRepository

class RegisterInfoViewModelFactory private constructor(
    val navigator: RegisterNavigator,
    val registerInfoRepository: RegisterInfoRepository,
    val marketingClause: Boolean
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(RegisterInfoViewModel::class.java) ->
                    RegisterInfoViewModel(navigator, registerInfoRepository, marketingClause)
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T

    companion object {

        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: RegisterInfoViewModelFactory? = null

        fun getInstance(activity: MainActivity, application: Application, marketingClause: Boolean) =
            INSTANCE ?: synchronized(RegisterInfoViewModelFactory::class.java) {
                INSTANCE ?: RegisterInfoViewModelFactory(
                    RegisterNavigator(Injection.createNavigationProvider(activity)),
                    Injection.provideRegisterInfoRepository(application),
                    marketingClause
                )
                    .also { INSTANCE = it }
            }


        @VisibleForTesting
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}