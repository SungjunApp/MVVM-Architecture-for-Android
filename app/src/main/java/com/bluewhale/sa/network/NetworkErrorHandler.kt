package com.bluewhale.sa.network

import com.bluewhale.sa.R
import com.bluewhale.sa.constant.AppConfig
import foundation.bluewhale.splashviews.network.BaseNetErrorHandler
import foundation.bluewhale.splashviews.network.ServerErrorState
import org.json.JSONObject
import retrofit2.HttpException
import java.net.UnknownHostException

class NetworkErrorHandler : BaseNetErrorHandler() {
    companion object{
        fun handleError(e: Throwable): Int {
            if (e is HttpException) {
                //Bwlog.e("winwalk_error", "code:" + httpException.code() + ",  errorCode: " + httpException.message());
                val body = e.response().errorBody()
                try {
                    val json = JSONObject(body!!.string())
                    val message = json.getJSONObject("error").getString("message")
                    //Bwlog.e("winwalk_error", "errorBody: " + json.toString());
                    //Bwlog.e("winwalk_error", "errorBody.errorCode: " + message);
                    return makeErrorMessageInternal(AppConfig.isProductionVersion(), message)
                } catch (e1: Exception) {
                    //                RootApplication.getInstance().setAnalyticsLog(FirebaseAnalyticsLog.serverError(e1.getMessage()));
                    e1.printStackTrace()
                    return R.string.error_json_parsing
                }

            } else if (e is UnknownHostException) {
                //            RootApplication.getInstance().setAnalyticsLog(FirebaseAnalyticsLog.serverError("UnknownHostException"));
                return R.string.error_no_connection
            }
            return 0
        }


        fun getServerErrorState(e: Throwable): ServerErrorState? {
            if (e is HttpException) {
                val body = e.response().errorBody()
                try {
                    return getServerErrorStateInternal(body!!.string())
                } catch (e1: Exception) {
                    e1.printStackTrace()
                }

            } else if (e is UnknownHostException) {
                //            RootApplication.getInstance().setAnalyticsLog(FirebaseAnalyticsLog.serverError("UnknownHostException"));
                return ServerErrorState(R.string.error_no_connection)
            }
            return ServerErrorState()
        }

        fun getServerErrorState(bodyString: String): ServerErrorState? {
            return getServerErrorStateInternal(bodyString)
        }

        private fun getServerErrorStateInternal(bodyString: String): ServerErrorState? {
            try {
                val json = JSONObject(bodyString)
                val message = json.getJSONObject("error").getString("message")
//            if (isNotParcebleError(sts.getStringRes()))
                //                RootApplication.getInstance().setAnalyticsLog(FirebaseAnalyticsLog.serverError(sts.getErrorMessage()));
                return ServerErrorState(AppConfig.isProductionVersion(), message)
            } catch (e1: Exception) {
                //            RootApplication.getInstance().setAnalyticsLog(FirebaseAnalyticsLog.serverError(e1.getMessage()));
                e1.printStackTrace()
            }

            return null
        }

        private fun makeErrorMessageInternal(isProductionVersion: Boolean, message: String): Int {
//        if (isNotParcebleError(errorMessageRes))
            //            RootApplication.getInstance().setAnalyticsLog(FirebaseAnalyticsLog.serverError(message));
            return makeErrorMessage(isProductionVersion, message)

        }

        private fun isNotParcebleError(errorMessageRes: Int): Boolean {
            return errorMessageRes == 0 || errorMessageRes == R.string.error_unhandled_message
        }
    }
}