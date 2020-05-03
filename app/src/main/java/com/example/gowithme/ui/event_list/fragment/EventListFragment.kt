package com.example.gowithme.ui.event_list.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import com.example.gowithme.R
import com.example.gowithme.ui.event_list.adatper.EventListPagedAdapter
import com.example.gowithme.ui.event_list.viewmodel.EventListViewMode
import kotlinx.android.synthetic.main.fragment_event_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class EventListFragment : Fragment() {

    private val eventListViewModel by viewModel<EventListViewMode>()
    private val eventListPagedAdapter by lazy { EventListPagedAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        eventListViewModel.loadEvents()

        eventList.adapter = eventListPagedAdapter

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        eventListViewModel.eventList.observe(viewLifecycleOwner, Observer(eventListPagedAdapter::submitList))
    }

}
