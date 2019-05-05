package com.sjsoft.app.model.trade

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class DTransactionList constructor(
    val payload: ArrayList<DTransaction>
) : Parcelable
