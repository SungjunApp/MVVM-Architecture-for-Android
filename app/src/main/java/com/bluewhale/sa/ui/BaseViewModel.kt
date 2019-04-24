package com.bluewhale.sa.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


open class BaseViewModel : ViewModel() {
    protected val _loading = MutableLiveData<Boolean>().apply { value = false }
    val loading: LiveData<Boolean>
        get() = _loading

    protected val _errorPopup = MutableLiveData<Int>()
    val errorPopup: LiveData<Int>
        get() = _errorPopup

    protected val _nextButton = MutableLiveData<Boolean>().apply { value = false }
    val nextButton: LiveData<Boolean>
        get() = _nextButton
}