package com.sjsoft.app.ui.trade

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.sjsoft.app.R
import com.sjsoft.app.ui.BaseFragment
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class TradeDetailFragment : BaseFragment() {
    override val titleResource: Int
        get() = R.string.title_registerInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_trade, container, false)
    }

    @Inject
    lateinit var factory: TradeDetailViewModel.TradeDetailViewModelFactory
    val mViewModel: TradeDetailViewModel by lazy {
        val viewModel = ViewModelProviders.of(this, factory)
            .get(TradeDetailViewModel::class.java)

        viewModel.tradeId = getTradeId()
        viewModel
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }


    fun getTradeId(): String {
        return arguments!!.getString(TRADE_ID)!!
    }

    companion object {
        const val TRADE_ID = "TRADE_ID"

        fun getInstance(tradeId: String): TradeDetailFragment {
            val f = TradeDetailFragment()
            val bundle = Bundle()
            bundle.putString(TRADE_ID, tradeId)
            f.arguments = bundle
            return f
        }
    }
}