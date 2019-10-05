package com.sjsoft.app.di

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.orhanobut.logger.Logger
import com.sjsoft.app.BuildConfig
import com.sjsoft.app.data.repository.PreferenceDataSource
import com.sjsoft.app.data.repository.PreferenceRepository
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {
    private val timeout_read = 30L
    private val timeout_connect = 20L
    private val timeout_write = 30L

    @Provides
    @Singleton
    fun provideOkHttpClient(interceptor: Interceptor): OkHttpClient {
        val ok = OkHttpClient.Builder()
            .connectTimeout(timeout_connect, TimeUnit.SECONDS)
            .readTimeout(timeout_read, TimeUnit.SECONDS)
            .writeTimeout(timeout_write, TimeUnit.SECONDS)

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
            ok.addInterceptor(logging)

        }

        /*dispatcher = Dispatcher()
        httpClientBuilder.dispatcher(dispatcher)*/

        ok.addInterceptor(interceptor)
        return ok.build()
    }

    @Provides
    @Singleton
    fun provideGSon(): Gson {
        return GsonBuilder()
            .create()
    }

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER_DOMAIN)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun getRequestInterceptor(storage: PreferenceDataSource): Interceptor {
        return Interceptor {
            val original = it.request()
            Log.e("pretty", "Interceptor.url.host: ${original.url.host}")
            Log.e("pretty", "Interceptor.url.path: ${original.url}")
            val requested = with(original) {
                val builder = newBuilder()

                builder.header("Accept", "application/json")
                /*storage.getCookie()?.also { cookie ->
                    builder.header("Authorization", "Bearer $cookie")

                }
                */
                builder.build()
            }

            val response = it.proceed(requested)
            val body = response.body
            val bodyStr = body?.string()
            Log.e("pretty", "**http-num: ${response.code}")
            Log.e("pretty", "**http-body: $body")


            /*val cookies = HashSet<String>()
            for (header in response.headers("Set-Cookie")) {
                cookies.add(header)

                val key = "general-jwt="
                if (header.contains(key)) {
                    var token = header
                    if (!token.isNullOrEmpty()) {
                        token = token.substring(token.indexOf(key) + key.length, token.indexOf(";"))
                        storage.setCookie(token)
                    }
                }
            }*/

            /*val isResponseOk = response.code / 100 == 2
            if (!isResponseOk && bodyStr != null) {
                val serverErrorState = NetworkErrorUtil.getServerErrorState(bodyStr)
                if (serverErrorState != null) {
                    if (serverErrorState.invalidSigniture()) {
                        storage.logout()
                    }
                }
            }*/

            val builder = response.newBuilder()

            builder.body(
                ResponseBody.create(
                    body?.contentType()
                    , bodyStr?.toByteArray()!!
                )
            ).build()
        }


    }

    @Singleton
    @Provides
    fun getUserPreferenceRepository(context: Context): PreferenceRepository {
        return PreferenceRepository(context)
    }

    /* fun clearAllCalls() {
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
     }*/
}