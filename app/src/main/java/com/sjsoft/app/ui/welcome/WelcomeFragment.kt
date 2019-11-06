package com.sjsoft.app.ui.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sjsoft.app.R
import com.sjsoft.app.di.Injectable
import com.sjsoft.app.ui.BaseFragment
import com.sjsoft.app.ui.zhome.HomeFragment
import com.sjsoft.app.util.replaceFragmentInActivity
import kotlinx.android.synthetic.main.fragment_welcome.*
import javax.inject.Inject

class WelcomeFragment : BaseFragment(), Injectable {
    override val titleResource: Int
        get() = R.string.title_welcome

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    val viewModel: WelcomeViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_welcome, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.command.observe(this, Observer {
            replaceFragmentInActivity(HomeFragment())
        })

        bt_next.setOnClickListener {
            viewModel.start()
        }
    }
}