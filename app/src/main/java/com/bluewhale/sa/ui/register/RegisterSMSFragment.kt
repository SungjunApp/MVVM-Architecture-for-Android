package com.bluewhale.sa.ui.register

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bluewhale.sa.Injection
import com.bluewhale.sa.R
import com.bluewhale.sa.data.source.register.DRequestToken
import com.bluewhale.sa.ui.BaseFragment
import tech.thdev.lifecycle.extensions.lazyInject

class RegisterSMSFragment : BaseFragment() {
    override val titleResource: Int
        get() = R.string.title_registerSMS

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_register_sms, container, false)
    }

    private val mViewModel: RegisterSMSViewModel by lazyInject(isActivity = true) {
        ViewModelProviders.of(this,
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    return RegisterSMSViewModel(
                        RegisterNavigator(Injection.createNavigationProvider(activity!!)),
                        Injection.provideRegisterSMSRepository(activity!!.application),
                        getMarketClause(),
                        getRequestToken() as DRequestToken
                    ) as T
                }
            }).get(RegisterSMSViewModel::class.java)
    }

    private fun getMarketClause(): Boolean {
        return arguments!!.getBoolean(RegisterInfoFragment.MARKETING_CLAUSE)
    }

    private fun getRequestToken(): Parcelable? {
        return arguments!!.getParcelable(REQUEST_TOKEN)
    }

    companion object {
        const val REQUEST_TOKEN = "REQUEST_TOKEN"
        fun getInstance(marketingClause: Boolean, requestToken: DRequestToken): RegisterSMSFragment {
            val f = RegisterSMSFragment()
            val bundle = Bundle()
            bundle.putBoolean(RegisterInfoFragment.MARKETING_CLAUSE, marketingClause)
            bundle.putParcelable(REQUEST_TOKEN, requestToken)
            f.arguments = bundle
            return f
        }
    }
}