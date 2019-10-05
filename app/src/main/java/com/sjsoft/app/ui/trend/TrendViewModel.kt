package com.sjsoft.app.ui.trend

import androidx.lifecycle.MutableLiveData
import com.sjsoft.app.constant.AppConfig
import com.sjsoft.app.data.repository.LottoDataSource
import com.sjsoft.app.room.Frequency
import com.sjsoft.app.ui.BaseViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import javax.inject.Inject


class TrendViewModel
@Inject constructor(val api: LottoDataSource) : BaseViewModel() {
    val list: MutableLiveData<List<Frequency>> = MutableLiveData()


    fun getList() {
        launchVMScope({
            val delay = async { delay(AppConfig.enteringDelay) }
            val result = async { api.loadAllFrequency() }

            delay.await()
            result.await()
            list.value = result.await()
        }, {

        })
    }


}