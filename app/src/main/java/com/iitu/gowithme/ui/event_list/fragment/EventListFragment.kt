package com.iitu.gowithme.ui.event_list.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.iitu.gowithme.MainActivity

import com.iitu.gowithme.R
import com.iitu.gowithme.data.network.event_list.EventListType
import com.iitu.gowithme.ui.event_list.adatper.EventListPagedAdapter
import com.iitu.gowithme.ui.event_list.viewmodel.EventListViewMode
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_event_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class EventListFragment : Fragment() {

    private val eventListViewModel by viewModel<EventListViewMode>()
    private val eventListPagedAdapter by lazy { EventListPagedAdapter() }
    private val safeArgs by navArgs<EventListFragmentArgs>()
    private val eventListType by lazy { safeArgs.eventListType }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle()
        (activity as MainActivity).toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
        eventListViewModel.loadEvents(eventListType, safeArgs.id)

        eventList.adapter = eventListPagedAdapter

        eventListPagedAdapter.setOnEventClickedListener {
            val direction = EventListFragmentDirections.actionEventListFragmentToEventPageFragment(it)
            findNavController().navigate(direction)
        }

    }

    private fun setTitle() {
        (activity as MainActivity).toolbar.title = when (eventListType) {
            EventListType.VIEWED_EVENTS -> getString(R.string.title_event_list_viewed_events)
            EventListType.MY_EVENTS -> getString(R.string.title_event_list_my_events)
            EventListType.SAVED_EVENTS ->getString(R.string.title_event_list_saved_events)

            EventListType.SPECIAL -> getString(R.string.title_special_list)
            EventListType.MOST_VIEWED -> getString(R.string.title_most_viewed_list)
            EventListType.UPCOMING -> getString(R.string.title_upcoming_list)
            EventListType.NEW -> getString(R.string.title_new_list)
            else -> getString(R.string.title_event_list)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        eventListViewModel.eventList.observe(viewLifecycleOwner, Observer(eventListPagedAdapter::submitList))
    }

}
