package com.bluewhale.sa.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bluewhale.sa.ui.BaseViewModel
import com.example.demo.network.APIRegister


class RegisterAgreementViewModel constructor(
    private val mNavigator: RegisterNavigator
) : BaseViewModel() {

    class RegisterAgreementViewModelFactory constructor(
        val navigator: RegisterNavigator
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>) =
            RegisterAgreementViewModel(navigator) as T
    }

    private val _items = MutableLiveData<RegisterAgreementData>()
        .apply { value = RegisterAgreementData(clause1 = false, clause2 = false, clause3 = false) }
    val items: LiveData<RegisterAgreementData>
        get() = _items

    /**
     *
     */
    fun setClauseAll(check: Boolean) {
        _items.value?.clause1 = check
        _items.value?.clause2 = check
        _items.value?.clause3 = check

        _nextButton.value = _items.value?.isPassable()
    }

    fun setClause1(check: Boolean) {
        _items.value?.clause1 = check

        _nextButton.value = _items.value?.isPassable()
    }

    fun setClause2(check: Boolean) {
        _items.value?.clause2 = check

        _nextButton.value = _items.value?.isPassable()
    }

    fun setClause3(check: Boolean) {
        _items.value?.clause3 = check
    }



    fun goNext() {
        if (_items.value?.isPassable()!!)
            _items.value?.clause3?.let { mNavigator.goRegisterInfoFragment(it) }
    }

    data class RegisterAgreementData(
        var clause1: Boolean,
        var clause2: Boolean,
        var clause3: Boolean
    ) {
        fun isPassable(): Boolean {
            return clause1 && clause2
        }
    }
}