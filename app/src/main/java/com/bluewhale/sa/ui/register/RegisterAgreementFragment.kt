package com.bluewhale.sa.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.bluewhale.sa.R
import com.bluewhale.sa.ViewModelFactory

class RegisterAgreementFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_register_agreement, container, false)
    }

    private lateinit var mViewModel: RegisterAgreementViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        ViewModelFactory.destroyInstance()

        mViewModel = ViewModelProviders.of(this)
            .get(RegisterAgreementViewModel::class.java)
    }
}