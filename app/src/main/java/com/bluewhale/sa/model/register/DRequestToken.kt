package com.bluewhale.sa.model.register

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DRequestToken constructor(val token: String) : Parcelable