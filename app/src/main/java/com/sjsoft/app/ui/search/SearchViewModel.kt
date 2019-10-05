package com.sjsoft.app.ui.search

import androidx.lifecycle.MutableLiveData
import com.sjsoft.app.constant.AppConfig
import com.sjsoft.app.global.SingleLiveEvent
import com.sjsoft.app.data.LottoMatch
import com.sjsoft.app.util.NetworkErrorUtil
import com.sjsoft.app.data.repository.LottoDataSource
import com.sjsoft.app.data.repository.PreferenceDataSource
import com.sjsoft.app.room.Lotto
import com.sjsoft.app.ui.BaseViewModel
import com.sjsoft.app.util.LottoTicket
import com.sjsoft.app.util.LottoUtil
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import javax.inject.Inject


class SearchViewModel
@Inject constructor(val api: LottoDataSource) : BaseViewModel() {
    val errorPopup: SingleLiveEvent<Int> = SingleLiveEvent()

    sealed class LottoUI {
        data class Data(val data: Lotto) : LottoUI()
        data class Loading(val showing: Boolean) : LottoUI()
        object Failur : LottoUI()
    }

    val lottoUI = MutableLiveData<LottoUI>()
    fun getWinner(drwNo: Int) {
        launchVMScope({
            lottoUI.value = LottoUI.Loading(true)

            //This gives a delay for the loading UI to be displayed on the screen for sure
            val delay = async { delay(AppConfig.enteringDelay) }
            val result = async { api.getWinder(drwNo) }

            delay.await()

            lottoUI.value = LottoUI.Data(result.await())
        }, {
            lottoUI.value = LottoUI.Failur
            errorPopup.value = NetworkErrorUtil.getErrorStringRes(it)
        })
    }
}

