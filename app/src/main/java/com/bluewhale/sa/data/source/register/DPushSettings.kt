package com.bluewhale.sa.data.source.register

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DPushSettings(
    var NOTICE: Boolean,
    var EVENT: Boolean,
    var PAYMENT: Boolean
) : Parcelable