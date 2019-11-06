package com.sjsoft.app.ui.zhome

import androidx.lifecycle.MutableLiveData
import com.sjsoft.app.global.SingleLiveEvent
import com.sjsoft.app.data.LottoMatch
import com.sjsoft.app.util.NetworkErrorUtil
import com.sjsoft.app.data.repository.LottoDataSource
import com.sjsoft.app.data.repository.PreferenceDataSource
import com.sjsoft.app.ui.BaseViewModel
import com.sjsoft.app.util.LottoTicket
import com.sjsoft.app.util.LottoUtil
import kotlinx.coroutines.async
import javax.inject.Inject


class HomeViewModel
@Inject constructor(val api: LottoDataSource, val pref: PreferenceDataSource) : BaseViewModel() {
    val generateButton = MutableLiveData<Boolean>().apply { value = false }
    val errorPopup: SingleLiveEvent<Int> = SingleLiveEvent()

    var searchedNumber: String = ""
    var eventNumber: String = ""
        set(value) {
            field = value
            val changed =
                value.isNotEmpty() && value != searchedNumber && value.toIntOrNull() != null
            generateButton.value = changed
            if (changed)
                lotto.value = ""
        }

    val lotto: MutableLiveData<String> = MutableLiveData()
    var generatedLotto: LottoTicket? = null
    fun generateLotto() {
        val lottoTicket = LottoUtil.generateWinNumbers()
        generatedLotto = lottoTicket

        lotto.value = lottoTicket.displayText
    }

    sealed class LottoMatchUI {
        data class Data(val data: LottoMatch) : LottoMatchUI()
        data class Loading(val showing: Boolean) : LottoMatchUI()
    }

    val lottoMatchUI: SingleLiveEvent<LottoMatchUI> = SingleLiveEvent()
    fun matchMineWithOfficial() {
        launchVMScope({
            lottoMatchUI.value = LottoMatchUI.Loading(true)

            //This gives a delay for the loading UI to be displayed on the screen for sure
            //val delay = async { delay(AppConfig.splashDelay) }
            val result = async { api.getWinder(eventNumber.toInt()) }

            //delay.await()

            generatedLotto?.also {
                lottoMatchUI.value = LottoMatchUI.Data(
                    LottoMatch(
                        result.await(),
                        it
                    )
                )
            }
        }, {
            lottoMatchUI.value = LottoMatchUI.Loading(false)
            errorPopup.value = NetworkErrorUtil.getErrorStringRes(it)
        })
    }

    val reservedDrwNo: SingleLiveEvent<Int> = SingleLiveEvent()
    fun queryReservedDrwNo() {
        pref.getReservedDrwNo()?.also {
            if (it.isNotEmpty()) {
                pref.setReservedDrwNo("")
                try {
                    reservedDrwNo.value = it.toInt()
                } catch (e: Exception) {
                }
            }
        }
    }
}

