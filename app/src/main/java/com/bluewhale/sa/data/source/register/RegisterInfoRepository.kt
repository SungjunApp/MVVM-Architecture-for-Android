package com.bluewhale.sa.data.source.register

class RegisterInfoRepository(private val registerDataSource: RegisterInfoDataSource) : RegisterInfoDataSource {
    override fun requestSMS(
        personalNumber1: String,
        personalNumber2: String,
        name: String,
        providerId: Int,
        mobileNumber: String,
        callback: RegisterInfoDataSource.CompletableCallback
    ) {
        registerDataSource.requestSMS(
            personalNumber1,
            personalNumber2,
            name,
            providerId,
            mobileNumber,
            callback
        )
    }


    companion object {

        private var INSTANCE: RegisterInfoRepository? = null

        /**
         * Returns the single instance of this class, creating it if necessary.

         * @param shiftDataSource the backend data source
         * *
         * @return the [RegisterInfoRepository] instance
         */
        @JvmStatic
        fun getInstance(registerDataSource: RegisterInfoDataSource) =
            INSTANCE ?: synchronized(RegisterInfoRepository::class.java) {
                INSTANCE ?: RegisterInfoRepository(registerDataSource)
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