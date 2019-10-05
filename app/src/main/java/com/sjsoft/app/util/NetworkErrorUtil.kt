package com.sjsoft.app.util

import com.sjsoft.app.R
import retrofit2.HttpException

object NetworkErrorUtil{
    fun getErrorStringRes(e: Throwable): Int {
        return when (e){
            is HttpException -> R.string.error_code
            is IllegalArgumentException -> R.string.error_argument
            else -> R.string.error_unknown
        }
    }
}