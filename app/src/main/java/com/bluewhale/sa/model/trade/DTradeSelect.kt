package com.bluewhale.sa.model.trade

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.math.BigDecimal


@Parcelize
data class DTradeSelect constructor(
    val _id: String,
    val price: BigDecimal,
    val amount: BigDecimal
) : Parcelable
