package com.iitu.gowithme.ui.event_list.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.iitu.gowithme.MainActivity
import com.iitu.gowithme.MainViewModel

import com.iitu.gowithme.R
import com.iitu.gowithme.data.network.event_list.EventListType
import com.iitu.gowithme.ui.event_list.adatper.EventListPagedAdapter
import com.iitu.gowithme.ui.event_list.viewmodel.EventListViewMode
import com.iitu.gowithme.ui.profile.fragment.ProfileFragmentDirections
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_event_list.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FriendsEventsFragment : Fragment() {

    private val eventListViewModel by viewModel<EventListViewMode>()
    private val eventListPagedAdapter by lazy { EventListPagedAdapter() }
    private val mainViewModel by sharedViewModel<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_friends_events, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
        eventListViewModel.loadEvents(EventListType.USER_EVENTS)

        eventList.adapter = eventListPagedAdapter

        eventListPagedAdapter.setOnEventClickedListener {
            val direction = EventListFragmentDirections.actionEventListFragmentToEventPageFragment(it)
            findNavController().navigate(direction)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mainViewModel.loginState.observe(viewLifecycleOwner, Observer {
            if (!it) {
                val direction =
                    ProfileFragmentDirections.actionGlobalLoginFragment(
                        R.id.nav_profile
                    )
                findNavController().navigate(direction)
            }
        })

        eventListViewModel.eventList.observe(viewLifecycleOwner, Observer(eventListPagedAdapter::submitList))
    }
}
