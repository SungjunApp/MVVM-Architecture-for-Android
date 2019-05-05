package com.sjsoft.app.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.sjsoft.app.R
import com.sjsoft.app.model.register.DRequestToken
import com.sjsoft.app.ui.BaseFragment
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class RegisterSMSFragment : BaseFragment() {
    override val titleResource: Int
        get() = R.string.title_registerSMS

    override fun onCreate(savedInstanceState:Bundle?){
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_register_sms, container, false)
    }

    /*private val mViewModel: RegisterSMSViewModel by lazyInject(isActivity = true) {
        ViewModelProviders.of(this,
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    return RegisterSMSViewModel(
                        RegisterNavigator(Injection.createNavigationProvider(activity!!)),
                        Injection.provideRegisterRepository(activity!!.application),
                        getMarketClause(),
                        getRequestToken() as DRequestToken
                    ) as T
                }
            }).get(RegisterSMSViewModel::class.java)
    }*/

    @Inject
    lateinit var factory: RegisterSMSViewModel.RegisterSMSViewModelFactory
    val mViewModel: RegisterSMSViewModel by lazy {
        val viewModel = ViewModelProviders.of(this, factory)
            .get(RegisterSMSViewModel::class.java)

        viewModel.marketingClause = getMarketClause()
        viewModel.requestToken = getRequestToken()
        viewModel
    }

    private fun getMarketClause(): Boolean {
        return arguments!!.getBoolean(RegisterInfoFragment.MARKETING_CLAUSE)
    }

    private fun getRequestToken(): DRequestToken? {
        return arguments!!.getParcelable(REQUEST_TOKEN) as DRequestToken
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