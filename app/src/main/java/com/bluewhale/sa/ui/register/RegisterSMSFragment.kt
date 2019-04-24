package com.bluewhale.sa.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.bluewhale.sa.MainActivity
import com.bluewhale.sa.R

class RegisterSMSFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_register_sms, container, false)
    }

    private lateinit var mViewModel: RegisterSMSViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewModel = activity?.run {
            ViewModelProviders.of(
                this, RegisterSMSViewModelFactory.getInstance(
                    activity = MainActivity(),
                    application = application,
                    marketingClause = getMarketClause()
                )
            )
                .get(RegisterSMSViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
    }

    private fun getMarketClause(): Boolean {
        return arguments!!.getBoolean(RegisterInfoFragment.MARKETING_CLAUSE)
    }

    companion object {
        fun getInstance(marketingClause: Boolean): RegisterSMSFragment {
            val f = RegisterSMSFragment()
            val bundle = Bundle()
            bundle.putBoolean(RegisterInfoFragment.MARKETING_CLAUSE, marketingClause)
            f.arguments = bundle
            return f
        }
    }
}