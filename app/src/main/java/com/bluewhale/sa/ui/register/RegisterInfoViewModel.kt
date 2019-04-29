package com.bluewhale.sa.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bluewhale.sa.constant.MobileProvider
import com.bluewhale.sa.network.NetworkErrorHandler
import com.bluewhale.sa.ui.BaseViewModel
import com.example.demo.network.APIRegister
import io.reactivex.Completable
import io.reactivex.Single


class RegisterInfoViewModel(
    private val mNavigator: RegisterNavigator,
    private val mRepository: APIRegister,
    private val marketingClause: Boolean
) : BaseViewModel() {

    private val _items = MutableLiveData<RegisterInfoData>()
        .apply { value = RegisterInfoData("", "", "", "", MobileProvider.UNSELECTED) }
    val items: LiveData<RegisterInfoData>
        get() = _items

    fun setName(name: String) {
        items.value?.name = name
    }

    fun setPersonalCode1(personalCode1: String) {
        items.value?.personalCode1 = personalCode1

        _nextButton.value = items.value?.isInfoFilledUp()
    }

    fun setPersonalCode2(personalCode2: String) {
        items.value?.personalCode2 = personalCode2

        _nextButton.value = items.value?.isInfoFilledUp()
    }

    fun setPhone(phone: String) {
        items.value?.phone = phone

        _nextButton.value = items.value?.isInfoFilledUp()
    }

    fun setProvider(provider: MobileProvider) {
        items.value?.provider = provider

        _nextButton.value = items.value?.isInfoFilledUp()
    }

    fun requestSMS(): Completable {
        _loading.value = true
        _nextButton.value = false

        return mRepository.requestSMS(
            items.value?.personalCode1 + items.value?.personalCode2,
            items.value?.name!!,
            items.value?.provider?.providerCode!!,
            items.value?.phone!!
        ).flatMap {
            mNavigator.goRegisterSMSFragment(marketingClause, it)

            Single.just(it)
        }.doOnSuccess {
            _loading.value = false
        }.doOnError {
            val stringRes = NetworkErrorHandler.handleError(it)
            if (stringRes > 0)
                _errorPopup.value = stringRes

            _loading.value = false
            _nextButton.value = items.value?.isInfoFilledUp()
        }.ignoreElement()
    }

    data class RegisterInfoData(
        var name: String,
        var personalCode1: String,
        var personalCode2: String,
        var phone: String,
        var provider: MobileProvider
    ) {

        fun isNameFull(): Boolean {
            return name.isNotEmpty()
        }

        fun isPersonalCode1Full(): Boolean {
            return personalCode1.isNotEmpty() && personalCode1.length == 6
        }

        fun isPersonalCode2Full(): Boolean {
            return personalCode2.isNotEmpty() && personalCode2.length == 1
        }

        fun isPhoneFull(): Boolean {
            return phone.isNotEmpty() && phone.length == 11
        }

        fun isProviderSelected(): Boolean {
            return provider != MobileProvider.UNSELECTED
        }

        fun isInfoFilledUp(): Boolean {
            return isNameFull() && isPersonalCode1Full() && isPersonalCode2Full() && isPhoneFull() && isProviderSelected()
        }
    }
}