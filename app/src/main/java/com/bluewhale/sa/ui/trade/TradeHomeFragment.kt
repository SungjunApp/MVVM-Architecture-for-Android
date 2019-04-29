package com.bluewhale.sa.ui.trade

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bluewhale.sa.Injection
import com.bluewhale.sa.R
import com.bluewhale.sa.ui.BaseFragment
import com.bluewhale.sa.ui.register.RegisterInfoFragment.Companion.MARKETING_CLAUSE
import kotlinx.android.synthetic.main.fragment_register_information.*
import tech.thdev.lifecycle.extensions.lazyInject

class TradeHomeFragment : BaseFragment() {
    override val titleResource: Int
        get() = R.string.title_registerInfo

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_register_information, container, false)
    }

    private val mViewModel: TradeHomeViewModel by lazyInject(isActivity = true) {
        ViewModelProviders.of(this,
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    return TradeHomeViewModel(
                        TradeNavigator(Injection.createNavigationProvider(activity!!)),
                        Injection.provideTradeRepository(activity!!.application)
                    ) as T
                }
            }).get(TradeHomeViewModel::class.java)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    }

    companion object {
        fun getInstance(): TradeHomeFragment {
            val f = TradeHomeFragment()
            val bundle = Bundle()
            f.arguments = bundle
            return f
        }
    }
}