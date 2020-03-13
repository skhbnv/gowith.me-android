package com.example.gowithme.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.gowithme.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class MapFragment : Fragment(), OnMapReadyCallback {

    var mMap: GoogleMap? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_map, container, false)
        val mapFrag: SupportMapFragment = (childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment)
        mapFrag.getMapAsync(this)

        return v
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap
        val sydney = LatLng(-34.0, 151.0)
        val sd = LatLng(-4561.0, 151.0)
        val sdd = LatLng(-1231.0, 151.0)
        val sddd = LatLng(-141.0, 151.0)
        val sdddd = LatLng(0.0, 151.0)
        mMap!!.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap!!.addMarker(MarkerOptions().position(sd).title("Marker in Sydney"))
        mMap!!.addMarker(MarkerOptions().position(sdd).title("Marker in Sydney"))
        mMap!!.addMarker(MarkerOptions().position(sddd).title("Marker in Sydney"))
        mMap!!.addMarker(MarkerOptions().position(sdddd).title("Marker in Sydney"))
        mMap!!.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}