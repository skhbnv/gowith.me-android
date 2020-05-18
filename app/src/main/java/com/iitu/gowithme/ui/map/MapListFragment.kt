package com.iitu.gowithme.ui.map

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.iitu.gowithme.R
import com.iitu.gowithme.data.network.ApiRepository
import com.iitu.gowithme.responses.GeneralEvents
import com.iitu.gowithme.ui.adapters.EventsAdapter
import com.iitu.gowithme.util.EventsKeyWord
import com.iitu.gowithme.util.RecyclerLayoutsType.FULL_SIZE
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_map_list.*
import java.io.IOException

class MapListFragment: Fragment() {
    private var adapter: EventsAdapter? = null
    private lateinit var navController: NavController
    private val mapViewModel by lazy {
        ViewModelProviders.of(activity!!, MapViewModel.MapFactory(ApiRepository()))
            .get(MapViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        initRecycler()
//        makeRequest()
        setEventsLocally()
        observeFields()
        mapToggleButton.setOnClickListener{
        }
    }

    private fun observeFields() {
        mapViewModel.events.observe(viewLifecycleOwner, Observer { list ->
            adapter?.initTheList(list)
        })
    }

    private fun setEventsLocally() {
        val jsonStr: String? = loadJsonFromAsset()
        val gson = Gson()
        val clicks =
            gson.fromJson<Array<GeneralEvents>>(jsonStr, Array<GeneralEvents>::class.java)
        var list = ArrayList<GeneralEvents>()

        for (click in clicks){
            list.add(click)
        }
        mapViewModel.events.value = list
    }

    private fun loadJsonFromAsset() : String?{
        var json: String? = null
        try {
            val inst = context!!.assets.open("general2")

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
            _onClick = { clickedEvent ->
                val bundle = Bundle()
                bundle.putSerializable(EventsKeyWord.EVENT_KEY_WORD, clickedEvent)
            },
            _context = (activity as Context),
            layoutType = FULL_SIZE
        )
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(activity)
    }
//    private fun makeRequest() = mapViewModel.getEvents()
}