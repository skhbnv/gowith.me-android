package com.example.gowithme.ui.event_page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.gowithme.BuildConfig
import com.example.gowithme.MainActivity
import com.example.gowithme.MainViewModel
import com.example.gowithme.R
import com.example.gowithme.data.models.response.EventResponse
import com.example.gowithme.databinding.FragmentEventPageBinding
import com.example.gowithme.ui.event_page.adapter.EventImageSliderAdapter
import com.example.gowithme.util.format
import com.example.gowithme.util.tenge
import com.example.gowithme.util.toDate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.koin.androidx.viewmodel.ext.android.viewModel

class EventPageFragment : Fragment(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap

    private val mainActivityInstance by lazy {
        (activity as MainActivity?)
    }

    private lateinit var binding: FragmentEventPageBinding

    private val eventPageViewModel by viewModel<EventPageViewModel>()
    private val glide by lazy { Glide.with(requireContext()) }
    private val safeArgs by navArgs<EventPageFragmentArgs>()
    private val eventId by lazy { safeArgs.eventId }
    private val eventImageSliderAdapter by lazy { EventImageSliderAdapter() }
    private val mainViewModel by viewModel<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_event_page, container, false)
        binding.executePendingBindings()
        binding.viewModel = eventPageViewModel

        val mapFrag: SupportMapFragment =
            (childFragmentManager.findFragmentById(R.id.eventMap) as SupportMapFragment)
        mapFrag.getMapAsync(this)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            subscribeOnEvent.setOnClickListener {
                eventPageViewModel.subscribeOnEvent(eventId)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mainViewModel.loginState.observe(viewLifecycleOwner, Observer {
            if (!it) {
                binding.subscribeOnEvent.visibility = View.GONE
            }
        })
        eventPageViewModel.eventDetailsUI.observe(viewLifecycleOwner, Observer {
            when(it) {
                is EventPageUI.EventLoaded -> {
                    setEventData(it.event)
                }
            }
        })
    }

    private fun setEventData(event: EventResponse) {
        with(binding) {
            title.text = event.title
            description.text = event.description
            viewCount.text = event.viewCounter.toString()
            price.text = if(event.price == 0) root.context.getString(R.string.text_free) else {
                root.context.getString(R.string.text_price, event.price.toString().tenge())
            }
            authorName.text = "${event.author.firstName} ${event.author.lastName}"
            event.author.image?.image?.let {
                glide.load(BuildConfig.BASE_URL + it.substring(1)).into(authorImage)
            }
            startDate.text = event.start.toDate().format("dd MMM, hh:mm")
            endDate.text = event.end.toDate().format("dd MMM, hh:mm")
            val sydney = LatLng(event.latitude, event.longitude)
            mMap.addMarker(MarkerOptions().position(sydney).title("Здесь"))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13f))
            imageSlider.setSliderAdapter(eventImageSliderAdapter)

            eventImageSliderAdapter.setImages(event.images)
        }
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        eventPageViewModel.getEventDetails(eventId)
    }
}