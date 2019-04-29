package com.bluewhale.sa.ui.register

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
import kotlinx.android.synthetic.main.fragment_register_information.*
import tech.thdev.lifecycle.extensions.lazyInject

class RegisterInfoFragment : BaseFragment() {
    override val titleResource: Int
        get() = R.string.title_registerInfo

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_register_information, container, false)
    }

    private val mViewModel: RegisterInfoViewModel by lazyInject(isActivity = true) {
        ViewModelProviders.of(this,
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    return RegisterInfoViewModel(
                        RegisterNavigator(Injection.createNavigationProvider(activity!!)),
                        Injection.provideRegisterRepository(activity!!.application),
                        getMarketClause()
                    ) as T
                }
            }).get(RegisterInfoViewModel::class.java)
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