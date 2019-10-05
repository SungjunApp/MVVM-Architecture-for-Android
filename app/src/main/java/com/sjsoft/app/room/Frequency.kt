package com.sjsoft.app.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

@Entity(tableName = "Frequency")
data class Frequency(
    @PrimaryKey
    val winNo: Int,     //번호
    var count: Int     //출현빈도
)