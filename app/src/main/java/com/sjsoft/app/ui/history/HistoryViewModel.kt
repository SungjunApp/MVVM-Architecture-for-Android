package com.sjsoft.app.ui.history

import androidx.lifecycle.MutableLiveData
import com.sjsoft.app.constant.AppConfig
import com.sjsoft.app.data.repository.LottoDataSource
import com.sjsoft.app.room.Lotto
import com.sjsoft.app.ui.BaseViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import javax.inject.Inject


class HistoryViewModel
@Inject constructor(val api: LottoDataSource) : BaseViewModel() {
    val list: MutableLiveData<List<Lotto>> = MutableLiveData()


    fun getList() {
        launchVMScope({
            val delay = async { delay(AppConfig.enteringDelay) }
            val result = async { api.loadAllLottos() }

            delay.await()
            result.await()
            list.value = result.await()
        }, {

        })
    }


}