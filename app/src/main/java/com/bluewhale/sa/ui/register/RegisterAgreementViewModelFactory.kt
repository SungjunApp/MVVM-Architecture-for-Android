package com.bluewhale.sa.ui.register

import android.annotation.SuppressLint
import android.app.Application
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bluewhale.sa.Injection
import com.bluewhale.sa.MainActivity

class RegisterAgreementViewModelFactory private constructor(
    val navigator: RegisterNavigator
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(RegisterAgreementViewModel::class.java) ->
                    RegisterAgreementViewModel(navigator)
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T

    companion object {

        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: RegisterAgreementViewModelFactory? = null

        fun getInstance(activity: MainActivity, application: Application) =
            INSTANCE ?: synchronized(RegisterAgreementViewModelFactory::class.java) {
                INSTANCE ?: RegisterAgreementViewModelFactory(
                    RegisterNavigator(Injection.createNavigationProvider(activity))
                )
                    .also { INSTANCE = it }
            }


        @VisibleForTesting
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}