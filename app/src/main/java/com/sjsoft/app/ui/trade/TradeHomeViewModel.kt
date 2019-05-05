package com.sjsoft.app.ui.trade

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sjsoft.app.model.trade.DStock
import com.sjsoft.app.network.NetworkErrorHandler
import com.sjsoft.app.ui.BaseViewModel
import com.sjsoft.app.network.api.APITrade
import io.reactivex.Single


class TradeHomeViewModel(
    val mNavigator: TradeNavigator,
    val mRepository: APITrade
) : BaseViewModel() {
    class TradeHomeViewModelFactory constructor(
        val navigator: TradeNavigator,
        val api: APITrade
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>) =
            TradeHomeViewModel(navigator, api) as T
    }

    private val _tradePage = MutableLiveData<Int>().apply { value = 0 }
    val tradePage: LiveData<Int>
        get() = _tradePage

    private val _tradeList = MutableLiveData<ArrayList<DStock>>().apply { value = arrayListOf() }
    val tradeList: LiveData<ArrayList<DStock>>
        get() = _tradeList

    private val _isFiltered = MutableLiveData<Boolean>().apply { value = false }
    val isFiltered: LiveData<Boolean>
        get() = _isFiltered

    fun getList(): Single<ArrayList<DStock>> {
        if (isFiltered.value!!)
            resetPage()

        return mRepository.getStockList(tradePage.value!!, 20)
            .flatMap {
                _tradePage.value = tradePage.value?.plus(1)
                _tradeList.value?.addAll(it.payload)
                Single.just(tradeList.value!!)
            }.doOnError {
                val stringRes = NetworkErrorHandler.handleError(it)
                if (stringRes > 0)
                    _errorPopup.value = stringRes
            }
    }

    fun resetPage() {
        _tradePage.value = 0
        _tradeList.value = arrayListOf()
        _isFiltered.value = false
    }

    fun getFilteredList(name: String): Single<ArrayList<DStock>> {
        resetPage()

        return mRepository.getFilteredStockList(name)
            .flatMap {
                _tradeList.value?.addAll(it.payload)

                _isFiltered.value = true
                Single.just(tradeList.value!!)
            }.doOnError {
                val stringRes = NetworkErrorHandler.handleError(it)
                if (stringRes > 0)
                    _errorPopup.value = stringRes
            }
    }

    fun goTradeDetail(tradeId: String) {
        mNavigator.goTradeDetailFragment(tradeId)
    }

}