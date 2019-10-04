package com.sjsoft.app.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "GameStat")
class GameStat constructor(
    @PrimaryKey
    @Expose var pkgId: String = "",
    @Expose var playTime: Long = 0,

    //@Transient

    var title: String = "",
    var iconResource: Int = 0,

    var firstTimeStamp:Long = 0L,
    var lastTimeStamp:Long = 0L,
    var totalTimeInForeground:Long = 0L

) {
    override fun toString(): String {
        return "pkgId: $pkgId, eventTitle: $title, playTime: $playTime, icon: $iconResource"
    }
}