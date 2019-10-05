package com.sjsoft.app.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sjsoft.app.R
import com.sjsoft.app.di.Injectable
import com.sjsoft.app.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_list.*
import javax.inject.Inject

class HistoryFragment : BaseFragment(), Injectable {
    override val titleResource: Int
        get() = R.string.title_history

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    val viewModel: HistoryViewModel by viewModels {
        viewModelFactory
    }

    val adapter: HistoryAdapter by lazy {
        HistoryAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.list.observe(this, Observer {
            adapter.replaceList(it)
        })

        recyclerView.adapter = adapter
        viewModel.getList()
    }


}