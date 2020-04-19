package com.example.gowithme.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.gowithme.MainActivity
import com.example.gowithme.R
import com.example.gowithme.data.network.ApiRepository
import com.example.gowithme.databinding.FragmentProfileBinding
import com.example.gowithme.responses.GeneralEvents
import com.example.gowithme.responses.ProfileInfo
import com.example.gowithme.ui.adapters.EventsAdapter
import com.example.gowithme.util.EventsKeyWord.EVENT_KEY_WORD
import com.example.gowithme.util.RecyclerLayoutsType.BRIEF_INFO
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

class ProfileFragment : Fragment() {
    private lateinit var profileBinding: FragmentProfileBinding
    private lateinit var adapter: EventsAdapter
    private lateinit var navController: NavController

    private val mainActivityInstance by lazy {
        (activity as MainActivity?)
    }

    private var adapterClickListener: (GeneralEvents) -> Unit = {
        val bundle = Bundle()
        bundle.putSerializable(EVENT_KEY_WORD, it)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        profileBinding.lifecycleOwner = viewLifecycleOwner
        setEventsLocally()
        return profileBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navController = Navigation.findNavController(view)
        observeFields()
    }

    private fun observeFields() {

    }

    private fun setEventsLocally() {

    }
}