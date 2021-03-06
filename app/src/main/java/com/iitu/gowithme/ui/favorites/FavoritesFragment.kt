package com.iitu.gowithme.ui.favorites

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
import androidx.recyclerview.widget.RecyclerView
import com.iitu.gowithme.R
import com.iitu.gowithme.data.network.ApiRepository
import com.iitu.gowithme.responses.GeneralEvents
import com.iitu.gowithme.ui.adapters.EventsAdapter
import com.iitu.gowithme.util.EventsKeyWord.EVENT_KEY_WORD
import com.iitu.gowithme.util.RecyclerLayoutsType.FULL_SIZE
import com.google.gson.Gson
import java.io.IOException

class FavoritesFragment : Fragment() {
    private lateinit var navController: NavController

    private val favoritesViewModel by lazy {
        ViewModelProviders.of(activity!!, FavoritesViewModel.FavoritesFactory(ApiRepository(apiService = null)))
            .get(FavoritesViewModel::class.java)
    }
    private var adapter: EventsAdapter? = null
    private var recyclerView: RecyclerView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        observeTheFields()
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    private fun observeTheFields() {
        favoritesViewModel.events.observe(viewLifecycleOwner, Observer { listOfEvents ->
            adapter?.initTheList(listOfEvents)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        initRecycler()
//        makeRequest()
        setEventsLocally()
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
        favoritesViewModel.events.value = list
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
                bundle.putSerializable(EVENT_KEY_WORD, clickedEvent)
            },
            _context = (activity as Context),
            layoutType = FULL_SIZE
        )
        recyclerView = view?.findViewById(R.id.recycler)
        recyclerView?.adapter = adapter
        recyclerView?.layoutManager = LinearLayoutManager(activity)
    }
    private fun makeRequest() = favoritesViewModel.getEvents()
}