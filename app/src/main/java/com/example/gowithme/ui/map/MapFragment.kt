package com.example.gowithme.ui.map


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.gowithme.R
import com.example.gowithme.databinding.FragmentMapBinding
import com.example.gowithme.network.ApiRepository
import com.example.gowithme.responses.GeneralEvents
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_map.*

class MapFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var navController: NavController
    private var mMap: GoogleMap? = null

    private lateinit var dataBinding: FragmentMapBinding

    private val mapViewModel by lazy {
        ViewModelProviders.of(activity!!, MapViewModel.MapFactory(ApiRepository()))
            .get(MapViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navController = Navigation.findNavController(view)
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        event_info.setOnClickListener { openEventPage() }
    }

    private fun openEventPage() {
        val bundle = Bundle()
        bundle.putSerializable("selectedGeneralEvent", mapViewModel.selectedGeneralEvents.value)
        navController.navigate(R.id.action_nav_map_to_eventPageFragment, bundle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_map, container, false)

        val mapFrag: SupportMapFragment =
            (childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment)
        mapFrag.getMapAsync(this)

        dataBinding.viewModel = mapViewModel
        dataBinding.executePendingBindings()
        dataBinding.lifecycleOwner = viewLifecycleOwner

        return dataBinding.root
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        mapViewModel.events.observe(viewLifecycleOwner, Observer { list ->
            setUpAllLocation(googleMap, list)
        })
        mapViewModel.selectedGeneralEvents.observe(viewLifecycleOwner, Observer { _ ->
            mapViewModel.markerInfoVisibility.set(View.VISIBLE)
        })
        setEventsLocally()
    }

    private fun setUpAllLocation(googleMap: GoogleMap?, list: ArrayList<GeneralEvents>) {
        mMap = googleMap
        var coords = LatLng(-34.0, 151.0)
        list.forEach { event ->
            coords = LatLng(event.location.latitude.toDouble(), event.location.longitude.toDouble())
            mMap!!.addMarker(MarkerOptions().position(coords)).tag = event.id
        }
        mMap!!.moveCamera(CameraUpdateFactory.newLatLng(coords))
        mMap!!.setMinZoomPreference(12.0f);
        mMap!!.setMaxZoomPreference(18.0f);
        mMap!!.setOnMarkerClickListener(this)
    }

    private fun setEventsLocally() {
        val jsonStr: String? = mapViewModel.loadJsonFromAsset(context!!.assets.open("general"))
        val gson = Gson()
        val clicks =
            gson.fromJson<Array<GeneralEvents>>(jsonStr, Array<GeneralEvents>::class.java)
        val list = ArrayList<GeneralEvents>()

        for (click in clicks) {
            list.add(click)
        }
        mapViewModel.events.value = list
    }

    override fun onMarkerClick(clickedMarker: Marker?): Boolean {
        mapViewModel.events.value?.forEach { event ->
            if (event.id == clickedMarker?.tag) {
                mapViewModel.selectedGeneralEvents.value = event
            }
        }
        return false
    }
}