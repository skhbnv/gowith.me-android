package com.example.gowithme.ui.create_new_event.fragment


import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
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

    companion object {
        private const val REQUEST_LOCATION_PERMISSION = 1
    }

    private lateinit var binding: FragmentMapAddressBinding
    private lateinit var map: GoogleMap
    private val createNewEventViewModel: CreateNewEventViewModel by sharedGraphViewModel(R.id.nav_create_new_event)
    private val geocoder by lazy { Geocoder(activity, Locale.getDefault()) }
    private var addressText = ""
    private var addressLatLng: LatLng? = null

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
            readyButton.setOnClickListener {
                addressLatLng?.let {
                    createNewEventViewModel.setAddressText(this@MapAddressFragment.addressText)
                    createNewEventViewModel.setLatLng(it.latitude, it.longitude)
                    findNavController().navigateUp()
                }
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.mapType = GoogleMap.MAP_TYPE_NORMAL
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(43.238949, 76.889709), 14f))
        setOnCameraIdleListener()
        enableMyLocation()
    }

    private fun setOnCameraIdleListener() {
        map.setOnCameraIdleListener {
            try {
                val latLng = map.cameraPosition.target
                val address = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
                binding.addressText.text = address.first().getAddressLine(0)
                addressLatLng = latLng
                addressText = address.first().getAddressLine(0)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun checkLocationPermissions(): Boolean =
        (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)

    private fun enableMyLocation() {
        if (checkLocationPermissions()) {
            map.isMyLocationEnabled = true
        } else {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION_PERMISSION)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.isNotEmpty() && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                enableMyLocation()
            }
        }
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
}
