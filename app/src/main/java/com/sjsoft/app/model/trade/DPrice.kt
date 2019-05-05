package com.sjsoft.app.model.trade

import android.os.Parcelable
import com.sjsoft.app.constant.Side
import kotlinx.android.parcel.Parcelize
import java.math.BigDecimal


@Parcelize
data class DPrice constructor(
    val price: BigDecimal,
    val amount: BigDecimal,
    val side: Side
) : Parcelable