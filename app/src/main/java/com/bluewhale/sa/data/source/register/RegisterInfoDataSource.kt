package com.bluewhale.sa.data.source.register

interface RegisterInfoDataSource {
    interface CompletableCallback {
        fun onComplete(requestToken: DRequestToken)
        fun onError(message: Int)
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