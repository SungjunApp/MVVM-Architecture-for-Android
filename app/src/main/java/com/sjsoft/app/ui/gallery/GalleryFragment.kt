package com.sjsoft.app.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.pixlee.pixleesdk.PXLAlbumSortType
import com.sjsoft.app.R
import com.sjsoft.app.di.Injectable
import com.sjsoft.app.ui.BaseFragment
import com.sjsoft.app.util.hide
import com.sjsoft.app.util.setShadowViewController
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
        recyclerView.setPaddingRelative(
            3.toPx()
            , 10.toPx()
            , 3.toPx()
            , 10.toPx()
        )

        recyclerView.setShadowViewController(v_shadow)

        viewModel.sortType.observe(this, Observer {
            changeTextViewColor(tab_recency, it, PXLAlbumSortType.RECENCY)
            changeTextViewColor(tab_random, it, PXLAlbumSortType.RANDOM)
            changeTextViewColor(tab_pixlee_shares, it, PXLAlbumSortType.PIXLEE_SHARES)
            changeTextViewColor(tab_pixlee_likes, it, PXLAlbumSortType.PIXLEE_LIKES)
            changeTextViewColor(tab_pixlee_popularity, it, PXLAlbumSortType.POPULARITY)
            changeTextViewColor(tab_pixlee_dynamic, it, PXLAlbumSortType.DYNAMIC)
        })

        viewModel.listUI.observe(this, Observer {
            v_loading.apply {
                if (it is GalleryViewModel.UIData.Loading) show() else hide()
            }

            when (it) {
                is GalleryViewModel.UIData.Loading -> {
                    adapter.submitList(null)
                }
                is GalleryViewModel.UIData.Data -> {
                    adapter.submitList(it.list)
                }
                is GalleryViewModel.UIData.Error -> {

                }
            }
        })

        tab_recency.setOnClickListener {
            viewModel.changeTab(PXLAlbumSortType.RECENCY)
        }

        tab_random.setOnClickListener {
            viewModel.changeTab(PXLAlbumSortType.RANDOM)
        }

        tab_pixlee_shares.setOnClickListener {
            viewModel.changeTab(PXLAlbumSortType.PIXLEE_SHARES)
        }

        tab_pixlee_likes.setOnClickListener {
            viewModel.changeTab(PXLAlbumSortType.PIXLEE_LIKES)
        }

        tab_pixlee_popularity.setOnClickListener {
            viewModel.changeTab(PXLAlbumSortType.POPULARITY)
        }

        tab_pixlee_dynamic.setOnClickListener {
            viewModel.changeTab(PXLAlbumSortType.DYNAMIC)
        }

        gridLayoutManager = GridLayoutManager(context, GRID_COUNT)
        recyclerView!!.layoutManager = gridLayoutManager
        gridLayoutManager?.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return 1
            }
        }

        recyclerView.adapter = adapter
        setupScrollListener()

        viewModel.changeTab(PXLAlbumSortType.RECENCY)
    }

    fun changeTextViewColor(
        textView: TextView,
        currentType: PXLAlbumSortType,
        targetType: PXLAlbumSortType
    ) {
        val color = if (currentType == targetType) R.color.active else R.color.inactive
        textView.setTextColor(ContextCompat.getColor(textView.context, color))
    }

    private fun setupScrollListener() {
        recyclerView.addOnScrollListener(object :
            androidx.recyclerview.widget.RecyclerView.OnScrollListener() {
            override fun onScrolled(
                recyclerView: androidx.recyclerview.widget.RecyclerView,
                dx: Int,
                dy: Int
            ) {
                super.onScrolled(recyclerView, dx, dy)
                gridLayoutManager?.apply {
                    viewModel.listScrolled(itemCount, childCount, findLastVisibleItemPosition())
                }

            }
        })
    }
}