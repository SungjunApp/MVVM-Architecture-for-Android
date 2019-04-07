package com.sjsoft.workmanagement.shift.work

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.sjsoft.workmanagement.*
import com.sjsoft.workmanagement.shift.BaseFragment
import com.sjsoft.workmanagement.shift.ShiftViewModel
import kotlinx.android.synthetic.main.fragment_work.*

class WorkFragment : BaseFragment() {
    override val titleResource: Int
        get() = R.string.title_work

    companion object {
        fun newInstance() = WorkFragment()
    }

    private lateinit var model: ShiftViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = activity?.run {
            ViewModelProviders.of(this, ViewModelFactory.getInstance(application)).get(ShiftViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        model.loadShifts()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_work, container, false)
    }

    lateinit var mAdapter : WorkAdapter
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        model.snackbarMessage.observe(this, Observer { event ->
            event.getContentIfNotHandled()?.let {
                context?.also { ctx ->
                    view?.also { view -> Snackbar.make(view, it, Snackbar.LENGTH_SHORT).show() }
                    //showSnackbar(ctx.getString(it), Snackbar.LENGTH_SHORT)
                }

            }
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
            if(textRes!=0)
                button_shift.setText(textRes)
            else
                button_shift.text = ""
        })

        button_shift.setOnClickListener{
            when (model.shiftButton.value) {
                UserStatus.READY -> model.startShift()
                UserStatus.LOADING -> { }
                UserStatus.WORKING -> model.endShift()
                else -> ""
            }
        }


        mAdapter = WorkAdapter(context!!, GlideApp.with(this).asDrawable())
        recyclerView.adapter = mAdapter

        val layoutManager = LinearLayoutManager(context)
        recyclerView!!.layoutManager = layoutManager

        model.shiftDetails.observe(this, Observer {
            mAdapter.setList(it)
        })
    }
}