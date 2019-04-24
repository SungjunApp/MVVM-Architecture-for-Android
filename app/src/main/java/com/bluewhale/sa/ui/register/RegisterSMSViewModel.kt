package com.bluewhale.sa.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bluewhale.sa.data.source.register.DRequestToken
import com.bluewhale.sa.data.source.register.DUser
import com.bluewhale.sa.data.source.register.RegisterSMSDataSource
import com.bluewhale.sa.data.source.register.RegisterSMSRepository
import com.bluewhale.sa.ui.BaseViewModel


class RegisterSMSViewModel(
    val navigator: RegisterNavigator,
    val registerSMSRepository: RegisterSMSRepository,
    val marketingClause: Boolean,
    val getRequestToken: DRequestToken
) : BaseViewModel() {

    private val _authCode = MutableLiveData<String>()
    val authCode: LiveData<String>
        get() = _authCode

    fun setAuthCode(authCode: String) {
        _authCode.value = authCode

        _nextButton.value = authCode.length == 6
    }

    fun verifyCode() {
        registerSMSRepository.verifyCode(
            getRequestToken.token,
            marketingClause,
            authCode.value!!,
            object : RegisterSMSDataSource.CompletableCallback {
                override fun onComplete(dUser: DUser) {
                    navigator.goHomeFragment()
                }

                override fun onError(message: Int) {
                    _errorPopup.apply { value = message }
                }
            }
        )
    }
}