package com.bluewhale.sa.ui.register

import android.annotation.SuppressLint
import android.app.Application
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bluewhale.sa.Injection
import com.bluewhale.sa.MainActivity
import com.bluewhale.sa.data.source.register.DRequestToken
import com.bluewhale.sa.data.source.register.RegisterSMSRepository

class RegisterSMSViewModelFactory private constructor(
    val navigator: RegisterNavigator,
    val registerSMSRepository: RegisterSMSRepository,
    val marketingClause: Boolean,
    val requestToken: DRequestToken
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(RegisterSMSViewModel::class.java) ->
                    RegisterSMSViewModel(navigator, registerSMSRepository, marketingClause, requestToken)
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T

    companion object {

        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: RegisterSMSViewModelFactory? = null

        fun getInstance(
            activity: MainActivity,
            application: Application,
            marketingClause: Boolean,
            requestToken: DRequestToken
        ) =
            INSTANCE ?: synchronized(RegisterSMSViewModelFactory::class.java) {
                INSTANCE ?: RegisterSMSViewModelFactory(
                    RegisterNavigator(Injection.createNavigationProvider(activity)),
                    Injection.provideRegisterSMSRepository(application),
                    marketingClause,
                    requestToken
                )
                    .also { INSTANCE = it }
            }


        @VisibleForTesting
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}