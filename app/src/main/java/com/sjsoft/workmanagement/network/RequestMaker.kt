package com.sjsoft.workmanagement.network

import android.content.Context
import android.util.Log
import com.google.gson.GsonBuilder
import com.orhanobut.logger.Logger
import com.sjsoft.workmanagement.BuildConfig
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

class RequestMaker constructor(val context: Context){
    private val timeout_read = 5L
    private val timeout_connect = 20L
    private val timeout_write = 30L

    val okHttpClient: OkHttpClient
    val dispatcher: Dispatcher
    val builder: Retrofit.Builder

    init {
        builder = Retrofit.Builder()
            .baseUrl(DomainInfo.URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))

        val httpClientBuilder = OkHttpClient.Builder()
        httpClientBuilder.connectTimeout(timeout_connect, TimeUnit.SECONDS)
        httpClientBuilder.readTimeout(timeout_read, TimeUnit.SECONDS)
        httpClientBuilder.writeTimeout(timeout_write, TimeUnit.SECONDS)
        //httpClientBuilder.protocols(getProtocols())
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                override fun log(message: String) {
                    if (isJSONValid(message))
                        Logger.json(message)
                    else
                        Log.d("pretty", message)
                }

                fun isJSONValid(jsonInString: String): Boolean {
                    try {
                        JSONObject(jsonInString)
                    } catch (ex: JSONException) {
                        try {
                            JSONArray(jsonInString)
                        } catch (ex1: JSONException) {
                            return false
                        }

                    }

                    return true
                }

            })
            logging.level = HttpLoggingInterceptor.Level.BODY
            httpClientBuilder.addInterceptor(logging)

        }

        dispatcher = Dispatcher()
        httpClientBuilder.dispatcher(dispatcher)

        httpClientBuilder.addInterceptor(object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                return convertInterceptToResponse(chain)
            }
        })
        okHttpClient = httpClientBuilder.build()
    }
    fun <T> createService(serviceClass: Class<T>): T {
        val retrofit = builder.client(okHttpClient).build()
        return retrofit.create(serviceClass)
    }

    @Throws(IOException::class)
    fun convertInterceptToResponse(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val responseOri = chain.proceed(getRequest(original))
        val responseBodyOri = responseOri.body()
        val bodyStrOri = responseBodyOri?.string()
        Log.e("pretty", "**http-status:" + responseOri.code())
        Log.e("pretty", "**http-body:$bodyStrOri")

        return getResponse(responseOri, responseBodyOri, bodyStrOri)
    }

    fun clearAllCalls() {
        Log.e("pretty", "queuedCallsCount: " + okHttpClient.dispatcher().queuedCallsCount())
        Log.e("pretty", "runningCallsCount: " + okHttpClient.dispatcher().runningCallsCount())

        for (call in okHttpClient.dispatcher().queuedCalls()) {
            call.cancel()
        }
        for (call in okHttpClient.dispatcher().runningCalls()) {
            call.cancel()
        }
    }

    private fun getResponse(response: Response, body: ResponseBody?, bodyStr: String?): Response {
        val builder = response.newBuilder()
        return builder
            .body(ResponseBody.create(body?.contentType(), bodyStr?.toByteArray()))
            .build()
    }

    private fun getRequest(request: Request): Request {
        val builder = request.newBuilder()

        builder.header("Authorization", "Deputy ${DomainInfo.CLIENT_SHASUM}")
        builder.header("Content-Type", "application/json")
        builder.method(request.method(), request.body())

        return builder.build()
    }
}