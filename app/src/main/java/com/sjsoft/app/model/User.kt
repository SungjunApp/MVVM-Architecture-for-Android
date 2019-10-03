package com.sjsoft.app.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User constructor(
    var objectId: String = "",
    var createdAt: String = "",
    var updatedAt: String = "",
    var email: String = "",
    var nickName: String = "",
    var goal: String = "",
    var targetTime: String = "",
    var face: String = "",
    var phone: String = "",
    var recentStudyLogObjectId: String = "",
    var mondayStart:Int = 0
) : Parcelable