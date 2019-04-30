package com.bluewhale.sa.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bluewhale.sa.model.register.DRequestToken
import com.bluewhale.sa.ui.BaseViewModel
import com.example.demo.network.APIRegister
import io.reactivex.Completable
import io.reactivex.Single


class RegisterSMSViewModel(
    val navigator: RegisterNavigator,
    val apiRegister: APIRegister
) : BaseViewModel() {
    class RegisterSMSViewModelFactory constructor(
        val navigator: RegisterNavigator,
        val apiRegister: APIRegister
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>) =
            RegisterSMSViewModel(navigator, apiRegister) as T
    }
    var marketingClause = false
    var requestToken: DRequestToken? = null

    private val _authCode = MutableLiveData<String>().apply { value = "" }
    val authCode: LiveData<String>
        get() = _authCode

    fun setAuthCode(authCode: String) {
        _authCode.value = authCode

        _nextButton.value = authCode.length == 6
    }

    fun verifyCode(): Completable {
        return apiRegister.verifySMS(
            authCode.value!!, requestToken!!.token
        ).flatMap {
            Single.just(it)
        }.ignoreElement()
    }
}