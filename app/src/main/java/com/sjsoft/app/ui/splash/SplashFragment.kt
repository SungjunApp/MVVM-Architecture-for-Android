package com.sjsoft.app.ui.splash

import android.os.Bundle
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
import com.sjsoft.app.ui.history.GalleryFragment
import com.sjsoft.app.ui.home.HomeFragment
import com.sjsoft.app.ui.welcome.WelcomeFragment
import com.sjsoft.app.util.*
import kotlinx.android.synthetic.main.fragment_splash.*
import javax.inject.Inject

class SplashFragment : BaseFragment(), Injectable {
    override val titleResource: Int
        get() = R.string.title_main

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bt_gallery.setSafeOnClickListener {
            addFragmentToActivity(GalleryFragment())
        }

        bt_upload.setSafeOnClickListener {
            addFragmentToActivity(GalleryFragment())
        }
    }
}