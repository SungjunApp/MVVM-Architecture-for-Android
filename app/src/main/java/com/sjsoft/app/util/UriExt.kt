package com.sjsoft.app.util

import android.net.Uri

fun Uri.queryDeepLinkParam(key: String): String? {
    var param: String? = null
    getQueryParameter(key)?.also {
        if (it.isNotEmpty()) {
            param = it
        }
    }
    return param
}