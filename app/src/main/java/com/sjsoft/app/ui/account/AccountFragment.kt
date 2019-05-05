package com.sjsoft.app.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sjsoft.app.R
import com.sjsoft.app.ui.BaseFragment
import com.sjsoft.app.ui.MainActivity
import com.sjsoft.app.view.addFragmentToActivity
import kotlinx.android.synthetic.main.fragment_account.*

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