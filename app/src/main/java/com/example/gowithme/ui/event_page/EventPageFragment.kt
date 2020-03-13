package com.example.gowithme.ui.event_page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.gowithme.MainActivity
import com.example.gowithme.R
import com.example.gowithme.databinding.FragmentEventPageBinding
import com.example.gowithme.network.ApiRepository
import com.example.gowithme.responses.DetailEvents
import com.example.gowithme.responses.GeneralEvents
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import com.synnapps.carouselview.ImageListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_event_page.*
import java.io.IOException

class EventPageFragment : Fragment(), OnMapReadyCallback{
    private var list = ArrayList<String?>()
    private var selectedEvent: GeneralEvents? = null
    var mMap: GoogleMap? = null
    private lateinit var dataBinding: FragmentEventPageBinding

    private val eventPageViewModel by lazy {
        ViewModelProviders.of(this, EventPageViewModel.EventPageFactory(ApiRepository()))
            .get(EventPageViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selectedEvent = arguments?.getSerializable("selectedGeneralEvent") as GeneralEvents
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_event_page,container, false)
        dataBinding.executePendingBindings()
        dataBinding.viewModel = eventPageViewModel

        setEventsLocally()
        val mapFrag: SupportMapFragment = (childFragmentManager.findFragmentById(R.id.eventMap) as SupportMapFragment)
        mapFrag.getMapAsync(this)

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeViewModel()
        eventPageViewModel.selectedGeneralEvents.value = selectedEvent
        (activity as MainActivity).nav_view.visibility = View.GONE
    }

    private fun observeViewModel() {
        eventPageViewModel.selectedDetailEvents.observe(viewLifecycleOwner, Observer { event ->
            setUpCarousel(event)
        })
    }

    private fun setUpCarousel(event: DetailEvents?) {
        val imageList = event?.images

        list = arrayListOf(
            imageList?.poster_url, imageList?.carousel_url1,
            imageList?.carousel_url2, imageList?.carousel_url3,
            imageList?.carousel_url4, imageList?.carousel_url5)

        carouselView.setImageListener(imageListener)
        carouselView.pageCount = list.size
    }

    private var imageListener: ImageListener =
        ImageListener { position, imageView ->
            Picasso.get().load(list[position]).into(imageView)
        }

    private fun setEventsLocally() {
        val jsonStr: String? = loadJsonFromAsset()
        val gson = Gson()
        val clicks =
            gson.fromJson<Array<DetailEvents>>(jsonStr, Array<DetailEvents>::class.java)
        var event:  DetailEvents? = null

        for (click in clicks) {
            if (click.id.toString() == selectedEvent?.id.toString()){
                event = click
            }
        }
        eventPageViewModel.selectedDetailEvents.value = event
    }

    private fun loadJsonFromAsset(): String? {
        var json: String? = null
        try {
            val inst = context!!.assets.open("detail")

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

    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap
        val sydney = LatLng(-34.0, 151.0)

        mMap!!.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap!!.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}