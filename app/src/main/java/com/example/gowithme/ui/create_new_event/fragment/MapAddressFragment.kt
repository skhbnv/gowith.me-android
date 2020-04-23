package com.example.gowithme.ui.create_new_event.fragment


import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.example.gowithme.R
import com.example.gowithme.databinding.FragmentMapAddressBinding
import com.example.gowithme.ui.create_new_event.viewmodel.CreateNewEventViewModel
import com.example.gowithme.util.inflateBinding
import com.example.gowithme.util.sharedGraphViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*


class MapAddressFragment : Fragment(), OnMapReadyCallback, SearchView.OnQueryTextListener {

    private lateinit var binding: FragmentMapAddressBinding
    private lateinit var map: GoogleMap
    private val createNewEventViewModel: CreateNewEventViewModel by sharedGraphViewModel(R.id.nav_create_new_event)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = inflater.inflateBinding(container, R.layout.fragment_map_address)
        (childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment).getMapAsync(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        with(binding) {
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.mapType = GoogleMap.MAP_TYPE_NORMAL
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(43.238949, 76.889709), 14f))
        setOnClickListener(map)
//        setPoiClick(map)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_toolbar, menu)
        val searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.isIconified = false
        searchView.setOnQueryTextListener(this)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    private fun setOnClickListener(map: GoogleMap) {
        val geocoder = Geocoder(activity, Locale.getDefault())
        val markerOptions = MarkerOptions()
        var marker: Marker? = null
        map.setOnMapClickListener { latLng ->
            try {
                Log.d("taaag", "latLng = ${latLng.latitude}, ${latLng.longitude}")
                val address = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
                marker?.remove()
                markerOptions.position(latLng)
                marker = map.addMarker(markerOptions)
                createNewEventViewModel.setLatLng(latLng.latitude, latLng.longitude)
                createNewEventViewModel.setAddressText(address.first().getAddressLine(0))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun setPoiClick(map: GoogleMap) {
        map.setOnPoiClickListener { poi ->
            val poiMarker = map.addMarker(
                MarkerOptions()
                    .position(poi.latLng)
                    .title(poi.name)
            )
            poiMarker.showInfoWindow()
        }
    }
}
