package com.sjsoft.app.model.trade

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class DPriceList constructor(
    val payload: ArrayList<DPrice>
) : Parcelable