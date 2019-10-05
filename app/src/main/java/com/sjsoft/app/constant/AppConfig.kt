package com.sjsoft.app.constant

import com.sjsoft.app.BuildConfig
import org.web3j.utils.Convert
import java.math.BigDecimal
import java.math.BigInteger

class AppConfig {
    companion object {
        const val prod = "prod"
        const val dev = "dev"
        const val stg = "stg"
        const val mock = "mock"

        const val BLUEWHALE_ACCOUNT = "BLUEWHALE_ACCOUNT"
        const val kycamlSupportEmail = "support@sumsub.com"

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


        val splashDelay = 3000L
        val enteringDelay = 400L
    }
}