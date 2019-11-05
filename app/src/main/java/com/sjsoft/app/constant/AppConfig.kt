package com.sjsoft.app.constant

import com.sjsoft.app.BuildConfig

class AppConfig {
    companion object {
        const val prod = "prod"
        const val dev = "dev"
        const val stg = "stg"
        const val mock = "mock"

        fun needDebugInfo(): Boolean {
            return !isProductionVersion || BuildConfig.DEBUG
        }

        val isProductionVersion: Boolean
            get() = BuildConfig.FLAVOR.contains(prod) && !BuildConfig.DEBUG

        val isDevVersion: Boolean
            get() = BuildConfig.FLAVOR.contains(dev)

        val isStgVersion: Boolean
            get() = BuildConfig.FLAVOR.contains(stg)

        val isMockVersion: Boolean
            get() = BuildConfig.FLAVOR.contains(mock)


        const val splashDelay = 3000L
        const val enteringDelay = 400L
        const val albumId = "5984962"
    }
    object DeepLinkParams {
        const val drwNo = "drwNo"
    }
}