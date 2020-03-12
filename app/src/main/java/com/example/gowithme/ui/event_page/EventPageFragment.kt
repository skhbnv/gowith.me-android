package com.example.gowithme.ui.event_page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.gowithme.MainActivity
import com.example.gowithme.R
import com.example.gowithme.network.ApiRepository
import com.example.gowithme.responses.DetailEvents
import com.example.gowithme.ui.dashboard.DashboardViewModel
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import com.synnapps.carouselview.ImageListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_event_page.*
import java.io.IOException

class EventPageFragment : Fragment() {
    private var list = ArrayList<String?>()
    private var id: String? = null
//    private var dataBinding:

    private val eventPageViewModel by lazy {
        ViewModelProviders.of(this, EventPageViewModel.EventPageFactory(ApiRepository()))
            .get(EventPageViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = arguments?.getString("eventId")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setEventsLocally()
        return inflater.inflate(R.layout.fragment_event_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeViewModel()
        (activity as MainActivity).nav_view.visibility = View.GONE
    }

    private fun observeViewModel() {
        eventPageViewModel.events.observe(viewLifecycleOwner, Observer { event ->
            setUpCarousel(event)
        })

    }

    private fun setUpCarousel(event: DetailEvents?) {
        val imageList = event?.images
        list = ArrayList(
            listOf(
                imageList?.poster_url,
                imageList?.carousel_url1,
                imageList?.carousel_url2,
                imageList?.carousel_url3,
                imageList?.carousel_url4,
                imageList?.carousel_url5
                )
        )
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
            if (click.id.toString() == id){

                event = click
            }
        }
        eventPageViewModel.events.value = event
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
}