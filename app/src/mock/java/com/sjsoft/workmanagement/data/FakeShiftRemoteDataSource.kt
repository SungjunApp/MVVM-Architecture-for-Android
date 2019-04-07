package com.sjsoft.workmanagement.data

import com.sjsoft.workmanagement.data.source.ShiftDataSource
import java.util.*

class FakeShiftRemoteDataSource : ShiftDataSource {
    val list: MutableList<Shift> = ArrayList()
    fun canStart() = list.isEmpty() || !list.first().end.isEmpty()
    fun canEnd() = !list.isEmpty() && list.first().end.isEmpty()

    override fun startShift(marker: ShiftHalf, callback: ShiftDataSource.CompletableCallback) {
        if (canStart()) {
            list.add(
                0,
                Shift(
                    (list.size + 1).toString(),
                    marker.time,
                    "",
                    marker.latitude,
                    marker.longitude,
                    "",
                    "",
                    "https://farm4.staticflickr.com/3937/15437546888_6a6f608e9c.jpg"
                )
            )
            callback.onComplete(list)
        } else {
            callback.onError()
        }
    }

    override fun endShift(marker: ShiftHalf, callback: ShiftDataSource.CompletableCallback) {
        if (canEnd()) {
            list.first().end = marker.time
            list.first().endLatitude = marker.latitude
            list.first().endLongitude = marker.longitude

            callback.onComplete(list)
        } else {
            callback.onError()
        }
    }

    override fun getShifts(callback: ShiftDataSource.LoadShiftCallback) {
        /*list.add(
            Shift(
                (list.size + 1).toString(),
                "awef",
                "",
                "",
                "",
                "",
                "",
                "https://farm4.staticflickr.com/3937/15437546888_6a6f608e9c.jpg"
            )
        )*/

        if (list.isEmpty())
            callback.onDataNotAvailable()
        else
            callback.onShiftsLoaded(list)
    }

//    fun toISO8601UTC(date: Date): String {
//        val tz = TimeZone.getTimeZone("UTC")
//        val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'")
//        df.timeZone = tz
//        return df.format(date)
//    }
}