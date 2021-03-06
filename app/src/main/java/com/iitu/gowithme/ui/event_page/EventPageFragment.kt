package com.iitu.gowithme.ui.event_page

import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.iitu.gowithme.MainActivity
import com.iitu.gowithme.MainViewModel
import com.iitu.gowithme.R
import com.iitu.gowithme.data.models.response.EventResponse
import com.iitu.gowithme.databinding.FragmentEventPageBinding
import com.iitu.gowithme.ui.event_page.adapter.EventImageSliderAdapter
import com.iitu.gowithme.util.format
import com.iitu.gowithme.util.tenge
import com.iitu.gowithme.util.toDate
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.iitu.gowithme.BuildConfig
import com.iitu.gowithme.data.network.user.UserListType
import com.iitu.gowithme.data.network.user.UserListTypeEnum
import com.iitu.gowithme.util.showAlert
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.Exception
import java.util.*

class EventPageFragment : Fragment(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap

    private val mainActivityInstance by lazy {
        (activity as MainActivity?)
    }

    private lateinit var binding: FragmentEventPageBinding
    private val geocoder by lazy { Geocoder(activity, Locale.getDefault()) }

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
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = eventPageViewModel

        val mapFrag: SupportMapFragment =
            (childFragmentManager.findFragmentById(R.id.eventMap) as SupportMapFragment)
        mapFrag.getMapAsync(this)
        eventPageViewModel.getEventDetails(eventId)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as MainActivity).toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
        with(binding) {
            subscribeOnEvent.setOnClickListener {
                eventPageViewModel.subscribeOnEvent(eventId)
                eventPageViewModel.getEventDetails(eventId)
            }
            imageSlider.setSliderAdapter(eventImageSliderAdapter)

            comments.setOnClickListener {
                val direction = EventPageFragmentDirections.actionEventPageFragmentToEventCommentsFragment(eventId)
                try {
                    findNavController().navigate(direction)
                } catch (e: Exception) {
                    e.stackTrace
                }
            }


        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mainViewModel.loginState.observe(viewLifecycleOwner, Observer {
            if (!it) {
                with(binding) {
                    subscribeOnEvent.visibility = View.GONE
                    chat.visibility = View.GONE
                    chatLink.visibility = View.GONE
                    likeCheckBox.setOnClickListener {
                        showAlert(context, message = getString(R.string.text_pls_auth), ok = {})
                        likeCheckBox.isChecked = false
                    }
                }
            } else {
                binding.likeCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
                    if(isChecked) {
                        eventPageViewModel.save(eventId)
                    } else {
                        eventPageViewModel.unSave(eventId)

                    }
                }
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
            price.text = if(event.price == 0) {
                root.context.getString(R.string.text_price, root.context.getString(R.string.text_free))
            } else {
                root.context.getString(R.string.text_price, event.price.toString().tenge())
            }
            authorName.text = "${event.author.firstName} ${event.author.lastName}"
            event.author.image?.image?.let {
                glide.load(BuildConfig.BASE_URL + it.substring(1)).into(authorImage)
            }
            startDate.text = context?.getString(R.string.text_start_date, event.start.toDate().format("dd MMM, hh:mm"))
            endDate.text = context?.getString(R.string.text_end_date, event.end.toDate().format("dd MMM, hh:mm"))
            val latLng = LatLng(event.latitude, event.longitude)
            mMap.addMarker(MarkerOptions().position(latLng).title("Здесь"))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13f))

            val address = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            addressText.text = address.first().getAddressLine(0)

            eventImageSliderAdapter.setImages(event.images)
            likeCheckBox.isChecked = event.isSaved

            subscribeCount.text = getString(R.string.text_users_subscribed, event.subscriptionsCounter.toString())
            Log.d("taaag", "event.isSubscribed ${event.isSubscribed}")
            if (mainViewModel.userInfo?.id == event.author.id) {
                subscribeOnEvent.visibility = View.GONE
            }
            chatLink.text = event.telegramChat
            if (event.isSubscribed || event.author.id == mainViewModel.userInfo?.id) {
                subscribeOnEvent.visibility = View.GONE
                chat.visibility = View.VISIBLE
                chatLink.visibility = View.VISIBLE
            } else {
                chat.visibility = View.GONE
                chatLink.visibility = View.GONE
            }

            subscribeCount.setOnClickListener {
                val userListType =
                    UserListType(
                        UserListTypeEnum.EVENT_SUBSCRIBERS,
                        eventId,
                        mainViewModel.userInfo?.id == event.author.id
                    )
                val direction = EventPageFragmentDirections.actionEventPageFragmentToUserListFragment(userListType)
                try {
                    findNavController().navigate(direction)
                } catch (e: Exception) {
                    e.stackTrace
                }
            }

        }
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
    }
}