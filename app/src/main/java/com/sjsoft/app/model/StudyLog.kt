package com.sjsoft.app.model

import com.google.gson.annotations.SerializedName
import com.sjsoft.app.network.api.BaseCollection

class StudyLog: BaseCollection() {
    var buffTime: Long = 0
    var startTime: Long = 0
    var studyDate = 0
    var studyDone = false
    var takeBreak = false
    var targetTime: Long = 0
    var timeZoneID = ""
    var timeZoneOffSet = 0

    //@SerializedName("userObject")
    @SerializedName("user")
    var userId = ""
    var joinObjectId = ""

    var studyTimeView = ""
    var studyTimeLong: Long = 0
    var achievementRate = 0
    var dayOfWeek = 0
    var targetTimeView = ""
}