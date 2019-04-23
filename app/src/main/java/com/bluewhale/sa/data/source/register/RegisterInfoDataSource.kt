package com.bluewhale.sa.data.source.register

interface RegisterInfoDataSource {
    interface CompletableCallback {
        fun onComplete(requestToken: dRequestToken)
        fun onError(message: String?)
    }

    fun requestSMS(
        personalNumber1: String,
        personalNumber2: String,
        name: String,
        providerId: Int,
        mobileNumber: String,
        callback: CompletableCallback
    )
}