package com.sjsoft.app.exception

class ServiceErrorException : Exception {
    companion object{
        const val UNKNOWN_ERROR = -1
        const val INVALID_DATA = 1
        const val KEY_STORE_ERROR = 1001
        const val FAIL_TO_SAVE_IV_FILE = 1002
        const val KEY_STORE_SECRET = 1003
        const val USER_NOT_AUTHENTICATED = 1004
        const val KEY_IS_GONE = 1005
        const val IV_OR_ALIAS_NO_ON_DISK = 1006
        const val INVALID_KEY = 1007
    }

    val code: Int

    constructor(code: Int, message: String?, throwable: Throwable?) : super(message, throwable) {
        this.code = code
    }

    constructor(code: Int, message: String?) : this(code, message, null)
    constructor(code: Int) : this(code, null)
}