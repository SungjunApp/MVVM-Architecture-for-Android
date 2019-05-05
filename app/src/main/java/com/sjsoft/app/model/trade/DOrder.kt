package com.sjsoft.app.model.trade

import android.os.Parcelable
import com.sjsoft.app.constant.Side
import kotlinx.android.parcel.Parcelize


@Parcelize
data class DOrder constructor(

    val userId: Int,
    val trader: String,
    val baseTokenAddress: String,
    val quoteTokenAddress: String,
    val baseTokenAmount: Int,
    val quoteTokenAmount: Int,
    val side: Side,

    val expireAt: Int


//    val userId: String,
//    val price: BigDecimal,
//    val amount: BigDecimal
) : Parcelable
