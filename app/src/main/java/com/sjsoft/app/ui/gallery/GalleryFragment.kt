package com.sjsoft.app.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnticipateOvershootInterpolator
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
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
            , 50.toPx()
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


        viewModel.loadMoreUI.observe(this, Observer {
            setupConstraintSet(it)
        })
        viewModel.listUI.observe(this, Observer {

            when (it) {
                is GalleryViewModel.UIData.LoadingShown -> {
                    v_loading.show()
                    adapter.submitList(null)
                }
                is GalleryViewModel.UIData.LoadingHide -> {
                    v_loading.hide()
                }
                is GalleryViewModel.UIData.Data -> {
                    v_loading.hide()
                    adapter.submitList(it.list)
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

        bt_more.setOnClickListener {
            viewModel.loadMore()
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

        viewModel.changeTab()
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

    private val mConstraintSet = ConstraintSet()
    private fun setupConstraintSet(showLoadMore:Boolean){
        mConstraintSet.clone(v_content_box)

        if (showLoadMore) {
            mConstraintSet.clear(bt_more.id, ConstraintSet.TOP)
            mConstraintSet.connect(
                bt_more.id, ConstraintSet.BOTTOM,
                ConstraintSet.PARENT_ID, ConstraintSet.TOP
            )
            mConstraintSet.connect(
                bt_more.id, ConstraintSet.BOTTOM,
                ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM
            )
        } else {
            mConstraintSet.connect(
                bt_more.id, ConstraintSet.TOP,
                ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM
            )
            mConstraintSet.clear(bt_more.id, ConstraintSet.BOTTOM)
        }

        val changeBounds = ChangeBounds()
        changeBounds.duration = 350
        changeBounds.interpolator = AnticipateOvershootInterpolator(1.0f)
        TransitionManager.beginDelayedTransition(v_content_box, changeBounds)

        mConstraintSet.applyTo(v_content_box)
    }
}