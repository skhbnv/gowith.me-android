package com.example.gowithme.ui.dashboard

import android.content.Context
import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gowithme.R
import com.example.gowithme.network.ApiRepository
import com.example.gowithme.responses.Event
import com.google.gson.Gson
import java.io.IOException


class DashboardFragment : Fragment() {

    private val dashboardViewModel by lazy {
        ViewModelProviders.of(this, DashboardViewModel.DashboardFactory(ApiRepository()))
            .get(DashboardViewModel::class.java)
    }
    private var adapter: EventsAdapter? = null
    private var recyclerView: RecyclerView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        observeTheFields()
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    private fun observeTheFields() {
        dashboardViewModel.events.observe(viewLifecycleOwner, Observer { listOfEvents ->
            adapter?.initTheList(listOfEvents)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
//        makeRequest()
        setEventsLocally()
    }

    private fun setEventsLocally() {
        val jsonStr: String? = loadJsonFromAsset()
        val gson = Gson()
        val clicks =
            gson.fromJson<Array<Event>>(jsonStr, Array<Event>::class.java)
        var list = ArrayList<Event>()

        for (click in clicks){
            list.add(click)
        }
        dashboardViewModel.events.value = list
    }

    private fun loadJsonFromAsset() : String?{
        var json: String? = null
        try {
            val inst = context!!.assets.open("a")

            val size = inst.available()
            val buffer = ByteArray(size)
            inst.read(buffer)
            inst.close()

            json = String(buffer)

        } catch (ex: IOException) {
            ex.printStackTrace()
        }
        return json
    }

    private fun initRecycler() {
        adapter = EventsAdapter(
            _onClick = {
                d("___", "sadasd")
            },
            _context = (activity as Context)
        )
        recyclerView = view?.findViewById(R.id.recycler)
        recyclerView?.adapter = adapter
        recyclerView?.layoutManager = LinearLayoutManager(activity)
    }
    private fun makeRequest() = dashboardViewModel.getEvents()
}