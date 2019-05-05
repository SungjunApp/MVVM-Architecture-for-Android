package com.sjsoft.app.model.source

import com.sjsoft.app.model.Shift
import com.sjsoft.app.model.ShiftHalf

interface ShiftDataSource {
    interface LoadShiftCallback {
        fun onShiftsLoaded(shifts: List<Shift>)
        fun onDataNotAvailable()
    }

    interface CompletableCallback {
        fun onComplete(shifts: List<Shift>)
        fun onError(message:String?)
    }

    fun startShift(marker: ShiftHalf, callback: CompletableCallback)
    fun endShift(marker: ShiftHalf, callback: CompletableCallback)
    fun getShifts(callback: LoadShiftCallback)

}