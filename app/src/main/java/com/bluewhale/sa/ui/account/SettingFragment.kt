package com.bluewhale.sa.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.bluewhale.sa.R
import com.bluewhale.sa.ui.BaseFragment
import javax.inject.Inject

class SettingFragment : BaseFragment() {
    companion object {
        fun getInstance(): SettingFragment {
            val f = SettingFragment()
//            val bundle = Bundle()
//            f.arguments = bundle
            return f
        }
    }

    override val titleResource: Int
        get() = R.string.title_setting

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    }


}