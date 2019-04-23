package com.bluewhale.sa.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bluewhale.sa.data.source.register.RegisterInfoDataSource
import com.bluewhale.sa.data.source.register.RegisterInfoRepository
import com.bluewhale.sa.data.source.register.dRequestToken


class RegisterInfoViewModel(
    private val registerRepository: RegisterInfoRepository
) : ViewModel() {
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    private val _errorPopup = MutableLiveData<Int>()
    val errorPopup: LiveData<Int>
        get() = _errorPopup

    private val _nextButton = MutableLiveData<Boolean>()
    val nextButton: LiveData<Boolean>
        get() = _nextButton

    private val _items = MutableLiveData<RegisterInfoData>()
        .apply { value = RegisterInfoData("", "", "", "", RegisterInfoData.Provider.UNSELECTED) }
    val items: LiveData<RegisterInfoData>
        get() = _items

    fun setName(name: String) {
        _items.value?.name = name
    }

    fun setPersonalCode1(personalCode1: String) {
        _items.value?.personalCode1 = personalCode1

        _nextButton.value = _items.value?.isInfoFull()
    }

    fun setPersonalCode2(personalCode2: String) {
        _items.value?.personalCode2 = personalCode2

        _nextButton.value = _items.value?.isInfoFull()
    }

    fun setPhone(phone: String) {
        _items.value?.phone = phone

        _nextButton.value = _items.value?.isInfoFull()
    }

    fun setProvider(provider: RegisterInfoData.Provider) {
        _items.value?.provider = provider

        _nextButton.value = _items.value?.isInfoFull()
    }

    fun requestSMS() {
        if (_items.value?.isInfoFull()!!) {
            val providerCode =
                when (_items.value?.provider) {
                    RegisterInfoData.Provider.SKT -> 0
                    RegisterInfoData.Provider.KT -> 1
                    RegisterInfoData.Provider.LG -> 2
                    RegisterInfoData.Provider.SKT_SUB -> 3
                    RegisterInfoData.Provider.KT_SUB -> 4
                    RegisterInfoData.Provider.LG_SUB -> 5
                    else -> -1
                }

            registerRepository.requestSMS(
                _items.value!!.personalCode1,
                _items.value!!.personalCode2,
                _items.value!!.name,
                providerCode,
                _items.value!!.phone,
                object : RegisterInfoDataSource.CompletableCallback {
                    override fun onComplete(requestToken: dRequestToken) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onError(message: String?) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                })
        }
    }
}