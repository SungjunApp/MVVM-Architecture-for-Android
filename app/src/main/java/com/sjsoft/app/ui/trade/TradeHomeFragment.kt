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

class TradeHomeFragment : BaseFragment() {
    override val titleResource: Int
        get() = R.string.title_registerInfo

    override fun onCreate(savedInstanceState:Bundle?){
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_trade, container, false)
    }

    @Inject
    lateinit var factory: TradeHomeViewModel.TradeHomeViewModelFactory
    val mViewModel: TradeHomeViewModel by lazy {
        ViewModelProviders.of(this, factory)
            .get(TradeHomeViewModel::class.java)

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