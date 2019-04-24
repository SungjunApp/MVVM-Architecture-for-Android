package com.bluewhale.sa.data.source.register

interface RegisterSMSDataSource {
    interface CompletableCallback {
        fun onComplete(dUser: DUser)
        fun onError(message: Int)
    }

    fun verifyCode(
        token: String,
        marketingClause: Boolean,
        authCode: String,
        callback: CompletableCallback
    )
}