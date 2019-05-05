package com.sjsoft.app.model.remote

import com.sjsoft.app.model.Shift
import com.sjsoft.app.model.ShiftHalf
import com.sjsoft.app.model.source.ShiftDataSource
import com.sjsoft.app.network.api.ShiftAPI
import retrofit2.Call
import retrofit2.Response

class ShiftRemoteDataSource constructor(val shiftAPI: ShiftAPI) : ShiftDataSource {
    override fun startShift(marker: ShiftHalf, callback: ShiftDataSource.CompletableCallback) {
        val call = shiftAPI.start(marker)
        call.enqueue(object : retrofit2.Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if(response.isSuccessful){
                    response.body()?.also {
                        callback.onError(it)
                    }

                    getShifts(object: ShiftDataSource.LoadShiftCallback{

                        override fun onShiftsLoaded(shifts: List<Shift>) {
                            callback.onComplete(shifts)
                        }
                        override fun onDataNotAvailable() {
                            callback.onError(null)
                        }
                    })

                }else{
                    callback.onError(response.message())
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                t.printStackTrace()
                callback.onError(t.message)
            }
        })
    }

    override fun endShift(marker: ShiftHalf, callback: ShiftDataSource.CompletableCallback) {
        val call = shiftAPI.end(marker)
        call.enqueue(object : retrofit2.Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if(response.isSuccessful){
                    response.body()?.also {
                        callback.onError(it)
                    }
                    getShifts(object: ShiftDataSource.LoadShiftCallback{
                        override fun onShiftsLoaded(shifts: List<Shift>) {
                            callback.onComplete(shifts)
                        }

                        override fun onDataNotAvailable() {
                            callback.onError(null)
                        }
                    })
                }else{
                    callback.onError(response.message())
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                t.printStackTrace()
                callback.onError(t.message)
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
