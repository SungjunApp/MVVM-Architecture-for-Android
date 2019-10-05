package com.android.app.util

import com.sjsoft.app.BuildConfig
import com.sjsoft.app.room.Lotto
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.net.HttpURLConnection
import java.net.UnknownHostException

object ExceptionTest{
    fun getHttpException(code:Int = HttpURLConnection.HTTP_UNAUTHORIZED, content:String = "{}"):HttpException {
        return HttpException(
            Response.error<Lotto>(
                code, ResponseBody.create(
                    "application/json".toMediaTypeOrNull(),
                    content
                )
            )
        )
    }

    fun getIllegalArgumentException(message:String = "check your params"):IllegalArgumentException {
        return IllegalArgumentException(message)
    }
}