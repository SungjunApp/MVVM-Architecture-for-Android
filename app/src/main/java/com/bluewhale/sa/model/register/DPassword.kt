package com.bluewhale.sa.model.register

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DPassword constructor(val password: String) : Parcelable