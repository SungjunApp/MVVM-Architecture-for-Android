package com.sjsoft.app.constant;

import com.sjsoft.app.BuildConfig;

public class AppConfig {
    public static class AppMode{
        public static final int production = 0;
        public static final int PGReview = 1;
    }

    //public static long cacheTime_nationalValue = TimeUnit.MINUTES.toMillis(1);

    public final static String prod = "prod";
    public final static String dev = "dev";
    public final static String stg = "stg";
    public final static String mock = "mock";

    public static int getAppMode() {
        return AppMode.production;
    }

    public static boolean isProductionVersion() {
        return BuildConfig.FLAVOR.contains(prod) && !BuildConfig.DEBUG;
    }

    public static boolean isDevVersion() {
        return BuildConfig.FLAVOR.contains(dev);
    }

    public static boolean isStgVersion() {
        return  BuildConfig.FLAVOR.contains(stg);
    }



    public static boolean isMockVersion(){
        return BuildConfig.FLAVOR.contains(mock);
    }
}
