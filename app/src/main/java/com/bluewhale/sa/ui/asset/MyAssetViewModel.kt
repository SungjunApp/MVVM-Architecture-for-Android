package com.bluewhale.sa.ui.asset

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bluewhale.sa.model.trade.DStock
import com.bluewhale.sa.network.NetworkErrorHandler
import com.bluewhale.sa.network.api.APIMyAsset
import com.bluewhale.sa.ui.BaseViewModel
import io.reactivex.Single


class MyAssetViewModel(
    val mNavigator: MyAssetNavigator,
    val mRepository: APIMyAsset
) : BaseViewModel() {
    class MyAssetViewModelFactory constructor(
        val navigator: MyAssetNavigator,
        val api: APIMyAsset
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>) =
            MyAssetViewModel(navigator, api) as T
    }

    private val _assetList = MutableLiveData<ArrayList<DStock>>().apply { value = arrayListOf() }
    val assetList: LiveData<ArrayList<DStock>>
        get() = _assetList

    fun getList(): Single<ArrayList<DStock>> {

        return mRepository.getMyAssetList()
            .flatMap {
                _assetList.value?.addAll(it.payload)
                Single.just(assetList.value!!)
            }.doOnError {
                val stringRes = NetworkErrorHandler.handleError(it)
                if (stringRes > 0)
                    _errorPopup.value = stringRes
            }
    }

    fun goVoteDetail(tradeId: String) {
        mNavigator.goVoteFragment(tradeId)
    }

    fun goTradeDetail(tradeId: String) {
        mNavigator.goTradeDetailFragment(tradeId)
    }

}