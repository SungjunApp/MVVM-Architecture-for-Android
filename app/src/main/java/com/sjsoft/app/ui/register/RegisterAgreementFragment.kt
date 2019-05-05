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

        checkBox_all.setOnCheckedChangeListener { buttonView, isChecked ->
            mViewModel.setClauseAll(isChecked)
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