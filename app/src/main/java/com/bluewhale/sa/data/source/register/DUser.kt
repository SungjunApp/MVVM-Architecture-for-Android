package com.bluewhale.sa.data.source.register

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DUser constructor(val _id: String, val marketingClause:Boolean) : Parcelable