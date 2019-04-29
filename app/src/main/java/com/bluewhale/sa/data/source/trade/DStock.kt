package com.bluewhale.sa.data.source.trade

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class DStock constructor(
    val _id: String,
    val name: String,           // 매물 이름
    val goingRate : String,     // 현재 시세
    val volatility: String,     // 가격 변동
    val tradeAmount: String,    // 오늘 총 거래 금액
    val totalValue: String      // 자산 총액
) : Parcelable