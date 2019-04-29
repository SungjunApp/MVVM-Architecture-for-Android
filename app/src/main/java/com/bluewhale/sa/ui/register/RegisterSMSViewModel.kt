package com.bluewhale.sa.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bluewhale.sa.data.source.register.DRequestToken
import com.bluewhale.sa.ui.BaseViewModel
import com.example.demo.network.APIRegister
import io.reactivex.Completable
import io.reactivex.Single


class RegisterSMSViewModel(
    private val mNavigator: RegisterNavigator,
    private val mRepository: APIRegister,
    val marketingClause: Boolean,
    val requestToken: DRequestToken
) : BaseViewModel() {

    private val _authCode = MutableLiveData<String>().apply { value = "" }
    val authCode: LiveData<String>
        get() = _authCode

    fun setAuthCode(authCode: String) {
        _authCode.value = authCode

        _nextButton.value = authCode.length == 6
    }

    fun verifyCode(): Completable {
        return mRepository.verifySMS(
            authCode.value!!, requestToken.token
        ).flatMap {
            Single.just(it)
        }.ignoreElement()
    }
}