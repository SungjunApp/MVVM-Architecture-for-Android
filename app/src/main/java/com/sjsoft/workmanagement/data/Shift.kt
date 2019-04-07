package com.sjsoft.workmanagement.data

import java.text.SimpleDateFormat

data class Shift constructor(
    val id: String,
    val start: String,
    var end: String = "",
    val startLatitude: String,
    val startLongitude: String,
    var endLatitude: String = "",
    var endLongitude: String = "",
    val image: String
) {


}