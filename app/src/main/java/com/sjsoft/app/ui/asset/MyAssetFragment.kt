package com.sjsoft.app.ui.asset

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.sjsoft.app.R
import com.sjsoft.app.ui.BaseFragment
import javax.inject.Inject

class MyAssetFragment : BaseFragment() {
    override val titleResource: Int
        get() = R.string.title_registerInfo

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_asset, container, false)
    }

    @Inject
    lateinit var factory: MyAssetViewModel.MyAssetViewModelFactory
    val mViewModel: MyAssetViewModel by lazy {
        ViewModelProviders.of(this, factory)
            .get(MyAssetViewModel::class.java)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    }

    companion object {
        fun getInstance(): MyAssetFragment {
            val f = MyAssetFragment()
            val bundle = Bundle()
            f.arguments = bundle
            return f
        }
    }
}