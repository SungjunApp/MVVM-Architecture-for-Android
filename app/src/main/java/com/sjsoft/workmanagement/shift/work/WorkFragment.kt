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


    }
}