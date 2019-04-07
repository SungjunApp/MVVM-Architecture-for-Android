package com.sjsoft.workmanagement.data.source

import com.sjsoft.workmanagement.data.Shift
import com.sjsoft.workmanagement.data.ShiftHalf

interface ShiftDataSource {
    interface LoadShiftCallback {
        fun onShiftsLoaded(shifts: List<Shift>)
        fun onDataNotAvailable()
    }

    interface CompletableCallback {
        fun onComplete(shifts: List<Shift>)
        fun onError()
    }

    fun startShift(marker: ShiftHalf, callback:CompletableCallback)
    fun endShift(marker: ShiftHalf, callback:CompletableCallback)
    fun getShifts(callback: LoadShiftCallback)

}