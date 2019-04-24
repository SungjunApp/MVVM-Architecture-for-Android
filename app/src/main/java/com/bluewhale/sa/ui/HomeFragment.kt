package com.bluewhale.sa.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bluewhale.sa.R
import com.bluewhale.sa.ui.register.RegisterSMSViewModel

class HomeFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_register_sms, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    companion object {
        fun getInstance(): HomeFragment {
            val f = HomeFragment()
            val bundle = Bundle()
            f.arguments = bundle
            return f
        }
    }
}