package com.bluewhale.sa.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.bluewhale.sa.R
import com.bluewhale.sa.ui.BaseFragment
import com.bluewhale.sa.ui.MainActivity
import com.bluewhale.sa.view.addFragmentToActivity
import com.bluewhale.sa.view.replaceFragmentInActivity
import kotlinx.android.synthetic.main.fragment_account.*
import javax.inject.Inject

class AccountFragment : BaseFragment() {
    override val titleResource: Int
        get() = R.string.menu_account

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        button_setting.setOnClickListener{
            (activity as MainActivity).addFragmentToActivity(
                (activity as MainActivity).frameLayoutId,
                SettingFragment()
            )
        }
    }
}