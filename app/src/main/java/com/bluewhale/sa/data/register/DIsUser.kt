package com.bluewhale.sa.data.register

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DIsUser constructor(
    val token: String,
    val isUserExist: Boolean
) : Parcelable