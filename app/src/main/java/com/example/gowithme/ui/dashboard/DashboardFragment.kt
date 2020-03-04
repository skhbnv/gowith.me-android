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
import com.example.gowithme.MainActivity
import com.example.gowithme.R
import com.example.gowithme.network.ApiRepository
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream


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
        getLocally()
    }

    private fun getLocally() {
        loadJsonFromAsset()
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

    fun loadJsonFromAsset(): String? {
        var json: String? = null
        try {
            val inst = (activity as MainActivity).assets.open("a.json")

            val size = inst.available()
            val buffer = ByteArray(size)
            inst.read(buffer)
            inst.close()

            json = String(buffer)

        } catch (ex: IOException) {
            ex.printStackTrace()
        }

//        try {
//            val inputStream: InputStream = context!!.assets.open("a.json")
//            json = inputStream.bufferedReader().use { it.readText() }
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }

        return json
    }
}