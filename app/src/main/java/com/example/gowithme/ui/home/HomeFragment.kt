package com.example.gowithme.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gowithme.R
import com.example.gowithme.data.models.ParentModel
import com.example.gowithme.data.network.ApiRepository
import com.example.gowithme.responses.GeneralEvents
import com.example.gowithme.ui.adapters.ParentAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_dashboard.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val homeViewModel by viewModel<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        homeViewModel.getE()
//        setEventsLocally()
//        observeViewModel()
    }

    private fun observeViewModel() {
        homeViewModel.events.observe(viewLifecycleOwner, Observer { list ->
            initRecycler(list as ArrayList<GeneralEvents>)
        })
    }

    private fun setEventsLocally() {
        val jsonStr: String? = homeViewModel.loadJsonFromAsset(context!!.assets.open("general"))
        val gson = Gson()
        val clicks =
            gson.fromJson<Array<GeneralEvents>>(jsonStr, Array<GeneralEvents>::class.java)
        val list = ArrayList<GeneralEvents>()

        for (click in clicks) {
            list.add(click)
        }
        homeViewModel.events.value = list
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