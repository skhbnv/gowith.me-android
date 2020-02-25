package com.example.gowithme.ui.dashboard

import android.content.Context
import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gowithme.R
import com.example.gowithme.network.ApiRepository
import com.example.gowithme.network.RetrofitApi
import kotlinx.android.synthetic.main.fragment_dashboard.*

class DashboardFragment : Fragment() {

    private val dashboardViewModel by lazy {
        ViewModelProviders.of(this, DashboardViewModel.DashboardFactory(ApiRepository()))
            .get(DashboardViewModel::class.java)
    }
    private var adapter: EventsAdapter? = null
    private var recyclerView: RecyclerView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        makeRequest()
    }

    private fun initRecycler() {
        adapter = EventsAdapter(
            _onClick = {
                d("___", "sadasd")
            },
            _context = (activity as Context))
        recyclerView = view?.findViewById(R.id.recycler)
        recyclerView?.adapter = adapter
        recyclerView?.layoutManager = LinearLayoutManager(activity)
    }

    private fun makeRequest() {
        dashboardViewModel.events.observe(this, Observer {listOfEvents ->
            adapter?.initTheList(listOfEvents)
        })

        dashboardViewModel.getEvents()
    }
}