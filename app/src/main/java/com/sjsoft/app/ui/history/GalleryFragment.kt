package com.sjsoft.app.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.sjsoft.app.R
import com.sjsoft.app.di.Injectable
import com.sjsoft.app.ui.BaseFragment
import com.sjsoft.app.util.hide
import com.sjsoft.app.util.show
import com.sjsoft.app.util.toPx
import kotlinx.android.synthetic.main.fragment_list.*
import javax.inject.Inject

class GalleryFragment : BaseFragment(), Injectable {
    override val titleResource: Int
        get() = R.string.title_gallery

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    val viewModel: GalleryViewModel by viewModels {
        viewModelFactory
    }

    val adapter: GalleryAdapter by lazy {
        GalleryAdapter(
            context!!,
            MarginInfo(
                0
                , 3.toPx()
                , 3.toPx()
            )
        )
    }
    internal var gridLayoutManager: GridLayoutManager? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    var GRID_COUNT: Int = 3
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recyclerView!!.setPaddingRelative(
            3.toPx()
            , 10.toPx()
            , 3.toPx()
            , 10.toPx()
        )

        viewModel.listUI.observe(this, Observer {
            v_loading.apply {
                if (it is GalleryViewModel.UIData.Loading) show() else hide()
            }

            when (it) {
                is GalleryViewModel.UIData.Data -> {
                    adapter.replaceList(it.list)
                }
                is GalleryViewModel.UIData.Error -> {

                }
            }

        })

        gridLayoutManager = GridLayoutManager(context, GRID_COUNT)
        recyclerView!!.layoutManager = gridLayoutManager
        gridLayoutManager?.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return 1
            }
        }
        recyclerView.adapter = adapter

        viewModel.getFirstList()
    }


}