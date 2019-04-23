package com.bluewhale.sa.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class RegisterAgreementViewModel : ViewModel() {
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    private val _errorPopup = MutableLiveData<Int>()
    val errorPopup: LiveData<Int>
        get() = _errorPopup

    private val _nextButton = MutableLiveData<Boolean>()
    val nextButton: LiveData<Boolean>
        get() = _nextButton

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
        if(_items.value?.isPassable()!!){

        }
    }
}