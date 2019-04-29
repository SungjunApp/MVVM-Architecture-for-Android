package com.bluewhale.sa.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bluewhale.sa.R
import com.bluewhale.sa.ui.BaseFragment
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_register_agreement.*
import javax.inject.Inject

class RegisterAgreementFragment : BaseFragment() {
    override val titleResource: Int
        get() = R.string.title_registerAgreement

    override fun onCreate(savedInstanceState:Bundle?){
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }


    /*private val mViewModel: RegisterAgreementViewModel by lazyInject(isActivity = true) {
        ViewModelProviders.of(this,
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    return RegisterAgreementViewModel(
                        RegisterNavigator(Injection.createNavigationProvider(activity!!))
                    ) as T
                }
            }).get(RegisterAgreementViewModel::class.java)
    }*/

    @Inject
    lateinit var factory: RegisterAgreementViewModel.RegisterAgreementViewModelFactory
    val mViewModel: RegisterAgreementViewModel by lazy {
        ViewModelProviders.of(this, factory)
            .get(RegisterAgreementViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_register_agreement, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mViewModel.nextButton.observe(this, Observer {
            bwtb_next.isEnabled = it
        })

        bwtb_next.setOnClickListener {
            mViewModel.goNext()
        }


        tv_clauseAll.setOnClickListener {
            mViewModel.setClauseAll(true)
        }
    }

    companion object {
        fun getInstance(): RegisterAgreementFragment {
            val f = RegisterAgreementFragment()
            val bundle = Bundle()
            f.arguments = bundle
            return f
        }
    }
}