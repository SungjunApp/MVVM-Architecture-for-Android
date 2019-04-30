package com.bluewhale.sa.ui.shift.work

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bluewhale.sa.GlideApp
import com.bluewhale.sa.R
import com.bluewhale.sa.ui.shift.ShiftViewModelFactory
import com.bluewhale.sa.ui.BaseFragment
import com.bluewhale.sa.ui.shift.ShiftViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_work.*
import javax.inject.Inject

class WorkDetailFragment : BaseFragment() {
    override val titleResource: Int
        get() = R.string.title_work_detail

    companion object {
        fun newInstance() = WorkDetailFragment()
    }

    private lateinit var model: ShiftViewModel

    @Inject
    lateinit var factory: ShiftViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = activity?.run {
            ViewModelProviders.of(this, factory)
                .get(ShiftViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        model.loadShifts()
    }

    override fun onDestroyView() {
        mOnScrollListener?.also {
            recyclerView.removeOnScrollListener(it)
        }
        super.onDestroyView()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_work, container, false)
    }

    var mOnScrollListener: RecyclerView.OnScrollListener? = null
    lateinit var mAdapter: WorkAdapter
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mOnScrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                setAlphaListShadow(shadowView, recyclerView.computeVerticalScrollOffset())
            }
        }

        mOnScrollListener?.also {
            recyclerView.addOnScrollListener(it)
        }


        model.snackbarInt.observe(this, Observer { event ->
            event.getContentIfNotHandled()?.let {
                view?.also { view ->
                    Snackbar.make(view, it, Snackbar.LENGTH_SHORT).show()
                }
            }
        })

        model.snackbarString.observe(this, Observer { event ->
            event.getContentIfNotHandled()?.let {
                view?.also { view ->
                    Snackbar.make(view, it, Snackbar.LENGTH_SHORT).show()
                }
            }
        })

        model.dataLoading.observe(this, Observer {
            progressBar.visibility =
                if (it) View.VISIBLE
                else View.GONE
        })

        model.retryAvailable.observe(this, Observer {
            button_retry.visibility =
                if (it) View.VISIBLE
                else View.GONE
        })

        model.shiftAvailable.observe(this, Observer {
            button_shift.isEnabled = it
        })

        model.shiftButton.observe(this, Observer {
            val textRes = when (it) {
                UserStatus.READY -> R.string.start_shift
                UserStatus.LOADING -> R.string.loading_shift
                UserStatus.WORKING -> R.string.end_shift
                else -> 0
            }
            if (textRes != 0)
                button_shift.setText(textRes)
            else
                button_shift.text = ""
        })

        button_retry.setOnClickListener {
            model.loadShifts()
        }

        button_shift.setOnClickListener {
            when (model.shiftButton.value) {
                UserStatus.READY -> model.startShift()
                UserStatus.LOADING -> {
                }
                UserStatus.WORKING -> model.endShift()
                else -> ""
            }
        }

        context?.also{
            mAdapter = WorkAdapter(it, GlideApp.with(this).asDrawable()){

            }
            recyclerView.adapter = mAdapter

            val layoutManager = LinearLayoutManager(context)
            layoutManager.reverseLayout = true
            layoutManager.stackFromEnd = true
            recyclerView!!.layoutManager = layoutManager
        }


        model.items.observe(this, Observer {
            mAdapter.setList(it)
        })
    }

    private val offSetRange = 150
    fun setAlphaListShadow(view: View?, offSet: Int) {
        if (view == null)
            return

        when {
            offSet in 0..offSetRange -> view.alpha = offSet / offSetRange.toFloat()
            offSet > offSetRange -> view.alpha = 1f
            else -> view.alpha = 0f
        }
    }
}