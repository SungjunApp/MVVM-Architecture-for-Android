package com.bluewhale.sa.ui.trade

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bluewhale.sa.network.NetworkErrorHandler
import com.bluewhale.sa.model.trade.DPrice
import com.bluewhale.sa.model.trade.DTradeMarketPrice
import com.bluewhale.sa.model.trade.DTradeSelect
import com.bluewhale.sa.model.trade.DTransaction
import com.bluewhale.sa.ui.BaseViewModel
import com.example.demo.network.APITrade
import io.reactivex.Single
import java.math.BigDecimal


class TradeDetailViewModel(
    val mNavigator: TradeNavigator,
    val mRepository: APITrade
) : BaseViewModel() {
    class TradeDetailViewModelFactory constructor(
        val navigator: TradeNavigator,
        val api: APITrade
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>) =
            TradeDetailViewModel(navigator, api) as T
    }

    lateinit var tradeId: String

    private val _priceList = MutableLiveData<ArrayList<DPrice>>().apply { value = arrayListOf() }
    val priceList: LiveData<ArrayList<DPrice>>
        get() = _priceList

    private val _transactionList = MutableLiveData<ArrayList<DTransaction>>().apply { value = arrayListOf() }
    val transactionList: LiveData<ArrayList<DTransaction>>
        get() = _transactionList

    private val _tradePrice = MutableLiveData<BigDecimal>().apply { value = BigDecimal(0) }
    val tradePrice: LiveData<BigDecimal>
        get() = _tradePrice

    private val _tradeAmount = MutableLiveData<BigDecimal>().apply { value = BigDecimal(0) }
    val tradeAmount: LiveData<BigDecimal>
        get() = _tradeAmount

    fun getPriceList(): Single<ArrayList<DPrice>> {

        return mRepository.getPriceList(tradeId)
            .flatMap {
                _priceList.value?.addAll(it.payload)
                Single.just(priceList.value!!)
            }.doOnError {
                val stringRes = NetworkErrorHandler.handleError(it)
                if (stringRes > 0)
                    _errorPopup.value = stringRes
            }
    }

    fun getTransactionList(): Single<ArrayList<DTransaction>> {

        return mRepository.getTransactionList(tradeId)
            .flatMap {
                _transactionList.value?.addAll(it.payload)
                Single.just(transactionList.value!!)
            }.doOnError {
                val stringRes = NetworkErrorHandler.handleError(it)
                if (stringRes > 0)
                    _errorPopup.value = stringRes
            }
    }

    fun setTradeAuto() {
        _tradePrice.value = BigDecimal(-1)
    }

    fun setTradePrice(price: String) {
        _tradePrice.value = BigDecimal(price)
    }

    fun setTradeAmount(amount: String) {
        _tradeAmount.value = BigDecimal(amount)
    }

    fun purchaseStock(): Single<DPrice> {

        return if (tradePrice.value!! < BigDecimal(0)) mRepository.purchaseStock(
            DTradeSelect(
                tradeId,
                tradePrice.value!!,
                tradeAmount.value!!
            )
        )
        else mRepository.purchaseStockMarketPrice(
            DTradeMarketPrice(
                tradeId,
                tradeAmount.value!!
            )
        ).flatMap {
            Single.just(it)
        }.doOnError {
            val stringRes = NetworkErrorHandler.handleError(it)
            if (stringRes > 0)
                _errorPopup.value = stringRes
        }
    }

    fun sellStock(): Single<DPrice> {

        return if (tradePrice.value!! < BigDecimal(0)) mRepository.sellStock(
            DTradeSelect(
                tradeId,
                tradePrice.value!!,
                tradeAmount.value!!
            )
        )
        else mRepository.sellStockMarketPrice(
            DTradeMarketPrice(
                tradeId,
                tradeAmount.value!!
            )
        ).flatMap {
            Single.just(it)
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