package com.bluewhale.sa.model.register

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DUser constructor(
    val id: Int,
    val email: String,
    val password: String,
    val walletAddress: String,
    val createdAt: String,
    val updatedAt: String
) : Parcelable