package com.bluewhale.sa.data.trade

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class DPriceList constructor(
    val payload: ArrayList<DPrice>
) : Parcelable