package com.bluewhale.sa.model.register

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DSignUp constructor(
    val token: String,
    val isUserExist: Boolean,
    val password: String,
    val marketing: Boolean = false,
    val fcmToken: String,
    val pushSettings: DPushSettings
) : Parcelable