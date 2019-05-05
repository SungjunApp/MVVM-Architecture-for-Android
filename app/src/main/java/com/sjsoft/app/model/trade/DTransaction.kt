package com.sjsoft.app.model.trade

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.math.BigDecimal


@Parcelize
data class DTransaction constructor(
    val _id: String,
    val price: BigDecimal,
    val amount: BigDecimal,
    val createdAt: Long,
    val updatedAt: Long
) : Parcelable
