package com.bluewhale.sa.ui.trade

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.bluewhale.sa.R
import com.bluewhale.sa.ui.BaseFragment
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
        return inflater.inflate(R.layout.fragment_register_information, container, false)
    }

    @Inject
    lateinit var factory: TradeHomeViewModelFactory
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