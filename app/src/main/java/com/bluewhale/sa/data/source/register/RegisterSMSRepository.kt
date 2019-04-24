package com.bluewhale.sa.data.source.register

class RegisterSMSRepository(private val RegisterSMSDataSource: RegisterSMSDataSource) : RegisterSMSDataSource {
    override fun verifyCode(
        token: String,
        marketingClause: Boolean,
        authCode: String,
        callback: RegisterSMSDataSource.CompletableCallback
    ) {
        RegisterSMSDataSource.verifyCode(
            token,
            marketingClause,
            authCode,
            callback
        )
    }


    companion object {
        private var INSTANCE: RegisterSMSRepository? = null

        /**
         * Returns the single instance of this class, creating it if necessary.

         * @param shiftDataSource the backend data source
         * *
         * @return the [RegisterSMSRepository] instance
         */
        @JvmStatic
        fun getInstance(registerSMSDataSource: RegisterSMSDataSource) =
            INSTANCE ?: synchronized(RegisterSMSRepository::class.java) {
                INSTANCE ?: RegisterSMSRepository(registerSMSDataSource)
                    .also { INSTANCE = it }
            }


        /**
         * Used to force [getInstance] to create a new instance
         * next time it's called.
         */
        @JvmStatic
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}