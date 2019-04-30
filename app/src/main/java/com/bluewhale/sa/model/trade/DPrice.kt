package com.bluewhale.sa.model.trade

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.math.BigDecimal


@Parcelize
data class DPrice constructor(
    val _id: String,
    val name: String,
    val price: BigDecimal,
    val amount: BigDecimal,
    val status: PriceStatus
) : Parcelable {
    enum class PriceStatus(val status: String) {
        SELL("SELL"),
        PURCHASE("PURCHASE")
    }
}