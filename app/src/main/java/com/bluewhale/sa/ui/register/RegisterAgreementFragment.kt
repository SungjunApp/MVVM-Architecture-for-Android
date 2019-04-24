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
import kotlinx.android.synthetic.main.fragment_register_agreement.*

class RegisterAgreementFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_register_agreement, container, false)
    }

    private lateinit var mViewModel: RegisterAgreementViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewModel = activity?.run {
            ViewModelProviders.of(
                this,
                RegisterAgreementViewModelFactory.getInstance(activity = MainActivity(), application = application)
            )
                .get(RegisterAgreementViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mViewModel.nextButton.observe(this, Observer {
            bwtb_next.isEnabled = it
        })

        bwtb_next.setOnClickListener {
            mViewModel.goNext()
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