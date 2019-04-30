package com.bluewhale.sa.model.trade

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.math.BigDecimal


@Parcelize
data class DTradeMarketPrice constructor(
    val _id: String,
    val amount : BigDecimal
) : Parcelable