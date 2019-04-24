package com.bluewhale.sa.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bluewhale.sa.MainActivity
import com.bluewhale.sa.R
import kotlinx.android.synthetic.main.fragment_register_information.*

class RegisterInfoFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_register_information, container, false)
    }

    private lateinit var mViewModel: RegisterInfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewModel = activity?.run {
            ViewModelProviders.of(
                this,
                RegisterInfoViewModelFactory.getInstance(
                    activity = MainActivity(),
                    application = application,
                    marketingClause = getMarketClause()
                )
            )
                .get(RegisterInfoViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mViewModel.errorPopup.observe(this, Observer {
            //todo : make make dialog
        })

        mViewModel.nextButton.observe(this, Observer {
            bwtb_next.isEnabled = it
        })

        bwtb_next.setOnClickListener {
            mViewModel.requestSMS()
        }
    }

    private fun getMarketClause(): Boolean {
        return arguments!!.getBoolean(MARKETING_CLAUSE)
    }

    companion object {
        const val MARKETING_CLAUSE = "MARKETING_CLAUSE"
        fun getInstance(marketingClause: Boolean): RegisterInfoFragment {
            val f = RegisterInfoFragment()
            val bundle = Bundle()
            bundle.putBoolean(MARKETING_CLAUSE, marketingClause)
            f.arguments = bundle
            return f
        }
    }
}