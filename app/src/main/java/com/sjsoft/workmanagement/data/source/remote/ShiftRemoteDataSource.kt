package com.sjsoft.workmanagement.data.source.remote

import com.sjsoft.workmanagement.data.Shift
import com.sjsoft.workmanagement.data.ShiftHalf
import com.sjsoft.workmanagement.data.source.ShiftDataSource
import com.sjsoft.workmanagement.network.api.ShiftAPI
import retrofit2.Call
import retrofit2.Response

class ShiftRemoteDataSource constructor(val shiftAPI: ShiftAPI) : ShiftDataSource {
    override fun startShift(marker: ShiftHalf, callback: ShiftDataSource.CompletableCallback) {
        val call = shiftAPI.start(marker)
        call.enqueue(object : retrofit2.Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                val list = response.body()
                if(list!=null){
                    getShifts(object:ShiftDataSource.LoadShiftCallback{
                        override fun onShiftsLoaded(shifts: List<Shift>) {
                            callback.onComplete(shifts)
                        }

                        override fun onDataNotAvailable() {
                            callback.onError()
                        }
                    })
                }else{
                    callback.onError()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                t.printStackTrace()
                callback.onError()
            }
        })
    }

    override fun endShift(marker: ShiftHalf, callback: ShiftDataSource.CompletableCallback) {
        val call = shiftAPI.end(marker)
        call.enqueue(object : retrofit2.Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                val list = response.body()
                if(list!=null){
                    getShifts(object:ShiftDataSource.LoadShiftCallback{
                        override fun onShiftsLoaded(shifts: List<Shift>) {
                            callback.onComplete(shifts)
                        }

                        override fun onDataNotAvailable() {
                            callback.onError()
                        }
                    })
                }else{
                    callback.onError()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                t.printStackTrace()
                callback.onError()
            }
        })
    }

    override fun getShifts(callback: ShiftDataSource.LoadShiftCallback) {
        val call = shiftAPI.shifts()
        call.enqueue(object : retrofit2.Callback<List<Shift>>{
            override fun onResponse(call: Call<List<Shift>>, response: Response<List<Shift>>) {
                val list = response.body()
                if(list!=null){
                    callback.onShiftsLoaded(list)
                }else{
                    callback.onDataNotAvailable()
                }
            }

            override fun onFailure(call: Call<List<Shift>>, t: Throwable) {
                t.printStackTrace()
                callback.onDataNotAvailable()
            }
        })
    }
}
