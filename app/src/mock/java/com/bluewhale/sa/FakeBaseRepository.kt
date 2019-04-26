package com.libs.meuuslibs.network

import com.facebook.stetho.json.ObjectMapper
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response


abstract class FakeBaseRepository {
    companion object {
        private const val DEFAULT_TYPE = "application/json; charset=utf-8"

        fun getErrorException(errorMsg: String): HttpException {
            val mapper = ObjectMapper()

            val message = JSONObject()
            message.put("message", errorMsg)

            val body = JSONObject()
            body.put("error", message)

            val jsonStr = mapper.convertValue(body, String::class.java)
            val responseBody = ResponseBody.create(MediaType.parse(DEFAULT_TYPE), jsonStr)
//            val responseBody = ResponseBody.create(MediaType.parse(DEFAULT_TYPE), body.toString())
            val response = Response.error<JSONObject>(400, responseBody)

            return HttpException(response)
        }
    }

    data class errorObject(val error: errorMessage)
    data class errorMessage(val message: String)
}