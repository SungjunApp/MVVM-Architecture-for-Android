package com.bluewhale.sa.data.source.trade

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class DStockList constructor(
    val payload: ArrayList<DStock>
) : Parcelable