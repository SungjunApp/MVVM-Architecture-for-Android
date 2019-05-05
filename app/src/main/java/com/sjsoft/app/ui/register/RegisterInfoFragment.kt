package com.sjsoft.app.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sjsoft.app.R
import com.sjsoft.app.ui.BaseFragment
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_register_information.*
import javax.inject.Inject

class RegisterInfoFragment : BaseFragment() {
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
    lateinit var factory: RegisterInfoViewModel.RegisterInfoViewModelFactory
    val mViewModel: RegisterInfoViewModel by lazy {
        val viewModel = ViewModelProviders.of(this, factory)
            .get(RegisterInfoViewModel::class.java)

        viewModel.marketingClause = getMarketClause()
        viewModel
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mViewModel.errorPopup.observe(this, Observer {
            //todo : make make dialog
        })

        mViewModel.nextButton.observe(this, Observer {
            bwtb_next.isEnabled = it
        })

        bwtb_next.setOnClickListener {
            disposables.add(mViewModel.requestSMS()
                .subscribe({}, {})
            )

        }
    }

    private fun getMarketClause(): Boolean {
        return arguments!!.getBoolean(MARKETING_CLAUSE)
    }

    companion object {
        const val MARKETING_CLAUSE = "MARKETING_CLAUSE"
        fun getInstance(marketingClause: Boolean): RegisterInfoFragment {
            val f = RegisterInfoFragment()
            val bundle = Bundle()
            bundle.putBoolean(MARKETING_CLAUSE, marketingClause)
            f.arguments = bundle
            return f
        }
    }
}