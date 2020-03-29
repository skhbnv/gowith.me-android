package com.example.gowithme.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gowithme.R
import com.example.gowithme.data.models.ParentModel
import com.example.gowithme.data.network.ApiRepository
import com.example.gowithme.responses.GeneralEvents
import com.example.gowithme.ui.adapters.ParentAdapter
import com.example.gowithme.util.EventsKeyWord
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_dashboard.*

class DashboardFragment : Fragment() {
    private val dashboardViewModel by lazy {
        ViewModelProviders.of(activity!!, DashboardViewModel.DashboardFactory(ApiRepository()))
            .get(DashboardViewModel::class.java)
    }
    private var onChildClick: ((GeneralEvents) -> Unit) = { child ->
        val bundle = Bundle()
        bundle.putSerializable(EventsKeyWord.EVENT_KEY_WORD, child)
//        navController.navigate(R.id.action_nav_home_to_eventPageFragment, bundle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        findNavController().navigate(R.id.action_nav_dashboard_to_loginFragment)

        setEventsLocally()
        observeViewModel()
    }

    private fun observeViewModel() {
        dashboardViewModel.events.observe(viewLifecycleOwner, Observer { list ->
            initRecycler(list as ArrayList<GeneralEvents>)
        })
    }

    private fun setEventsLocally() {
        val jsonStr: String? = dashboardViewModel.loadJsonFromAsset(context!!.assets.open("general2"))
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
        parentList.add(ParentModel("Горы зовут - надо идти", list, 0))
        parentList.add(ParentModel("Будет жарко", list,0))
        parentList.add(ParentModel("Заголовок 1", list,0))
        parentList.add(ParentModel("Заголовок 2", list,0))

        rv_parent.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = ParentAdapter(parentList, onChildClick)
        }

    }
}