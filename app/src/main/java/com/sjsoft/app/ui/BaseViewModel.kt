package com.sjsoft.app.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

//    override fun onCleared() {
//        super.onCleared()
//    }


    fun launchVMScope(block: suspend CoroutineScope.() -> Unit, errorReturn: (Throwable) -> Unit) {
        val handler = CoroutineExceptionHandler { _, e ->
            e.printStackTrace()
            errorReturn(e)
        }
        viewModelScope.launch(handler) {
            block()
        }
    }
}