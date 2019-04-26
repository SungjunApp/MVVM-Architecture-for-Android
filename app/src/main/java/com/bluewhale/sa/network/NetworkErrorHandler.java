package com.bluewhale.sa.network;

import com.bluewhale.sa.R;
import com.bluewhale.sa.constant.AppConfig;
import foundation.bluewhale.splashviews.network.BaseNetErrorHandler;
import foundation.bluewhale.splashviews.network.ServerErrorState;
import okhttp3.ResponseBody;
import org.json.JSONObject;
import retrofit2.HttpException;

import java.net.UnknownHostException;

public class NetworkErrorHandler extends BaseNetErrorHandler {
    public static int handleError(Throwable e) {
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            //Bwlog.e("winwalk_error", "code:" + httpException.code() + ",  errorCode: " + httpException.message());
            ResponseBody body = httpException.response().errorBody();
            try {
                JSONObject json = new JSONObject(body.string());
                String message = json.getJSONObject("error").getString("message");
                //Bwlog.e("winwalk_error", "errorBody: " + json.toString());
                //Bwlog.e("winwalk_error", "errorBody.errorCode: " + message);
                return makeErrorMessageInternal(AppConfig.isProductionVersion(), message);
            } catch (Exception e1) {
//                RootApplication.getInstance().setAnalyticsLog(FirebaseAnalyticsLog.serverError(e1.getMessage()));
                e1.printStackTrace();
                return R.string.error_json_parsing;
            }
        } else if (e instanceof UnknownHostException) {
//            RootApplication.getInstance().setAnalyticsLog(FirebaseAnalyticsLog.serverError("UnknownHostException"));
            return R.string.error_no_connection;
        }
        return 0;
    }


    public static ServerErrorState getServerErrorState(Throwable e) {
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            ResponseBody body = httpException.response().errorBody();
            try {
                return getServerErrorStateInternal(body.string());
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } else if (e instanceof UnknownHostException) {
//            RootApplication.getInstance().setAnalyticsLog(FirebaseAnalyticsLog.serverError("UnknownHostException"));
            return new ServerErrorState(R.string.error_no_connection);
        }
        return new ServerErrorState();
    }

    public static ServerErrorState getServerErrorState(String bodyString) {
        return getServerErrorStateInternal(bodyString);
    }

    private static ServerErrorState getServerErrorStateInternal(String bodyString) {
        try {
            JSONObject json = new JSONObject(bodyString);
            String message = json.getJSONObject("error").getString("message");
            ServerErrorState sts = new ServerErrorState(AppConfig.isProductionVersion(), message);
//            if (isNotParcebleError(sts.getStringRes()))
//                RootApplication.getInstance().setAnalyticsLog(FirebaseAnalyticsLog.serverError(sts.getErrorMessage()));
            return sts;
        } catch (Exception e1) {
//            RootApplication.getInstance().setAnalyticsLog(FirebaseAnalyticsLog.serverError(e1.getMessage()));
            e1.printStackTrace();
        }
        return null;
    }

    private static int makeErrorMessageInternal(boolean isProductionVersion, String message) {
        int errorMessageRes = makeErrorMessage(isProductionVersion, message);
//        if (isNotParcebleError(errorMessageRes))
//            RootApplication.getInstance().setAnalyticsLog(FirebaseAnalyticsLog.serverError(message));
        return errorMessageRes;

    }

    private static boolean isNotParcebleError(int errorMessageRes) {
        return errorMessageRes == 0 || errorMessageRes == R.string.error_unhandled_message;
    }
}
