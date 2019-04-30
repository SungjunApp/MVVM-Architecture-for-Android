package com.bluewhale.sa.data.trade

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.math.BigDecimal


@Parcelize
data class DPrice constructor(
    val _id: String,
    val name: String,
    val price : BigDecimal,
    val number : BigDecimal,
    val status: PriceStatus
) : Parcelable{
    enum class PriceStatus{
        SELL,
        PURCHASE
    }
}