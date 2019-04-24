package com.bluewhale.sa.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bluewhale.sa.ui.BaseViewModel


class RegisterAgreementViewModel constructor(
    val navigator: RegisterNavigator
) : BaseViewModel() {
    private val _items = MutableLiveData<RegisterAgreementData>()
        .apply { value = RegisterAgreementData(clause1 = false, clause2 = false, clause3 = false) }
    val items: LiveData<RegisterAgreementData>
        get() = _items

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
            _items.value?.clause3?.let { navigator.goRegisterInfoFragment(it) }
    }
}