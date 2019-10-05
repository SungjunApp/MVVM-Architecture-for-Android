package com.sjsoft.app.ui.splash

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sjsoft.app.R
import com.sjsoft.app.di.Injectable
import com.sjsoft.app.ui.BaseFragment
import com.sjsoft.app.ui.main.MainFragment
import com.sjsoft.app.ui.welcome.WelcomeFragment
import com.sjsoft.app.util.*
import kotlinx.android.synthetic.main.fragment_splash.*
import javax.inject.Inject

class SplashFragment : BaseFragment(), Injectable {
    override val titleResource: Int
        get() = R.string.title_splash

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    val viewModel: SplashViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.uiData.observe(this, Observer {
            when(it){
                is SplashViewModel.UIData.Main -> {
                    replaceFragmentInActivity(MainFragment())
                }

                is SplashViewModel.UIData.Welcome -> {
                    replaceFragmentInActivity(WelcomeFragment())
                }

                is SplashViewModel.UIData.Loading ->{
                    v_loading.show()
                }

                is SplashViewModel.UIData.Error -> {
                    v_loading.hide()
                    showRetryDialog()
                }
            }

        })

        viewModel.syncData()
    }

    fun showRetryDialog(){
        val builder = context?.let { AlertDialog.Builder(it) }
        if (builder != null) {
            builder.setMessage(R.string.sync_data_retry)
            builder.setPositiveButton(R.string.button_retry) { _, _ ->
                viewModel.syncData()
            }
            builder.setNegativeButton(android.R.string.cancel){ _, _ ->
                activity?.finish()
            }
            builder.show()
        }
    }
}