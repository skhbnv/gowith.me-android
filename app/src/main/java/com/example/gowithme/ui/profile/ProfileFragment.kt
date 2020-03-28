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
import com.example.gowithme.databinding.FragmentProfileBinding
import com.example.gowithme.data.network.ApiRepository
import com.example.gowithme.responses.GeneralEvents
import com.example.gowithme.responses.ProfileInfo
import com.example.gowithme.ui.adapters.EventsAdapter
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
        bundle.putSerializable("selectedGeneralEvent", it)
        navController.navigate(R.id.action_profile_to_event_page, bundle)
    }

    private val profileViewModel by lazy {
        ViewModelProviders.of(activity!!, ProfileViewModel.ProfileFactory(ApiRepository()))
            .get(ProfileViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        profileBinding.viewModel = profileViewModel
        profileBinding.lifecycleOwner = viewLifecycleOwner
        setEventsLocally()
        return profileBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navController = Navigation.findNavController(view)
        observeFields()
    }

    private fun observeFields() {
        profileViewModel.profileInfo.observe(viewLifecycleOwner, Observer { profileInfo ->
            profileInfo?.let {
                val userName = "${profileInfo.user.first_name} ${profileInfo.user.last_name}"
                mainActivityInstance?.toolbar?.title_bar?.text = userName

                adapter = EventsAdapter(
                    context!!,
                    profileInfo.last_activity as ArrayList<GeneralEvents>,
                    _onClick = adapterClickListener, _briefInfo = true
                )
                profileViewModel.lastActivityAdapter.value = adapter
            }
        })
    }

    private fun setEventsLocally() {
        val jsonStr: String? =
            profileViewModel.loadJsonFromAsset(context!!.assets.open("profile_info"))
        val clicks =
            Gson().fromJson<ProfileInfo>(jsonStr, ProfileInfo::class.java)
        profileViewModel.profileInfo.value = clicks
    }
}