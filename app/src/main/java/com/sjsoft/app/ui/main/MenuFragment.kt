package com.sjsoft.app.ui.main

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.sjsoft.app.R
import com.sjsoft.app.di.Injectable
import com.sjsoft.app.ui.BaseFragment
import com.sjsoft.app.ui.gallery.GalleryFragment
import com.sjsoft.app.ui.upload.UploadFragment
import com.sjsoft.app.util.addFragmentToActivity
import com.sjsoft.app.util.setSafeOnClickListener
import kotlinx.android.synthetic.main.fragment_menu.*
import javax.inject.Inject

class MenuFragment : BaseFragment(), Injectable {
    override val titleResource: Int
        get() = R.string.title_main

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    val viewModel: MenuViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bt_gallery.setSafeOnClickListener {
            addFragmentToActivity(GalleryFragment())
        }

        bt_upload.setSafeOnClickListener {
            addFragmentToActivity(UploadFragment())
        }
    }
}