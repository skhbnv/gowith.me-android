package com.example.gowithme.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gowithme.R
import com.example.gowithme.models.ParentModel
import com.example.gowithme.network.ApiRepository
import com.example.gowithme.responses.GeneralEvents
import com.example.gowithme.ui.adapters.ParentAdapter
import com.example.gowithme.ui.favorites.FavoritesViewModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_dashboard.*
import java.io.InputStream

class DashboardFragment : Fragment() {
    private val dashboardViewModel by lazy {
        ViewModelProviders.of(activity!!, DashboardViewModel.DashboardFactory(ApiRepository()))
            .get(DashboardViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setEventsLocally()
        observeViewModel()
    }

    private fun observeViewModel() {
        dashboardViewModel.events.observe(viewLifecycleOwner, Observer { list ->
            initRecycler(list as ArrayList<GeneralEvents>)
        })
    }

    private fun setEventsLocally() {
        val jsonStr: String? = dashboardViewModel.loadJsonFromAsset(context!!.assets.open("general"))
        val gson = Gson()
        val clicks =
            gson.fromJson<Array<GeneralEvents>>(jsonStr, Array<GeneralEvents>::class.java)
        val list = ArrayList<GeneralEvents>()

        for (click in clicks) {
            list.add(click)
        }
        dashboardViewModel.events.value = list
    }

    private fun initRecycler(list: ArrayList<GeneralEvents>) {
        val parentList = ArrayList<ParentModel>()
        parentList.add(ParentModel("Горы зовут - надо идти", list ))
        parentList.add(ParentModel("Будет жарко", list ))
        parentList.add(ParentModel("Заголовок 1", list ))
        parentList.add(ParentModel("Заголовок 2", list ))

        rv_parent.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = ParentAdapter(parentList)
        }

    }
}