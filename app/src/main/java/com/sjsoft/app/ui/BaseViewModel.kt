package com.sjsoft.app.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {
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