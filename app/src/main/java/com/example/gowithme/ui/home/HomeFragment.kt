package com.example.gowithme.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gowithme.MainViewModel
import com.example.gowithme.R
import com.example.gowithme.data.models.ParentModel
import com.example.gowithme.responses.GeneralEvents
import com.example.gowithme.ui.adapters.ParentAdapter
import com.example.gowithme.ui.home.adapter.HomeMainRecyclerAdapter
import com.example.gowithme.ui.home.adapter.HorizontalEventRecyclerAdapter
import com.example.gowithme.util.EventsKeyWord.EVENT_KEY_WORD
import com.example.gowithme.util.RecyclerLayoutsType.COMING_SOON
import com.example.gowithme.util.RecyclerLayoutsType.NEARBY_EVENTS
import com.example.gowithme.util.RecyclerLayoutsType.POSTERS
import com.example.gowithme.util.showToast
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_create_new_event.*
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.log

class HomeFragment : Fragment() {

    private lateinit var navController: NavController

    private val homeViewModel by viewModel<HomeViewModel>()
    private val mainViewModel by sharedViewModel<MainViewModel>()

    private var onChildClick: ((GeneralEvents) -> Unit) = { child ->
        val bundle = Bundle()
        bundle.putSerializable(EVENT_KEY_WORD, child)
    }

    private val homeMainRecyclerAdapter by lazy { HomeMainRecyclerAdapter() }
    private val newEventsRecyclerAdapter by lazy { HorizontalEventRecyclerAdapter() }
    private val mostViewedEventsRecyclerAdapter by lazy { HorizontalEventRecyclerAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        homeViewModel.getEvents()
        with(view) {
            newEventsRecycler.adapter = newEventsRecyclerAdapter
            mostViewedEventsRecycler.adapter = mostViewedEventsRecyclerAdapter
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mainViewModel.loginState.observe(viewLifecycleOwner, Observer {
            "Is login $it".showToast(context)
        })

//        homeViewModel.eventsLD.observe(viewLifecycleOwner, Observer {
//            Log.d("taaag", "eventsLD")
//            homeMainRecyclerAdapter.addEventList("Events", it)
//        })
//
        homeViewModel.newEvents.observe(viewLifecycleOwner, Observer(newEventsRecyclerAdapter::setEvents))
        homeViewModel.mostViewedEvents.observe(viewLifecycleOwner, Observer(mostViewedEventsRecyclerAdapter::setEvents))
    }
}