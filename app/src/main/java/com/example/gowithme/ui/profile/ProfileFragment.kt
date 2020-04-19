package com.example.gowithme.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.gowithme.MainActivity
import com.example.gowithme.R
import com.example.gowithme.databinding.FragmentProfileBinding
import com.example.gowithme.responses.GeneralEvents
import com.example.gowithme.ui.adapters.EventsAdapter
import com.example.gowithme.util.EventsKeyWord.EVENT_KEY_WORD
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var adapter: EventsAdapter
    private lateinit var navController: NavController

    private val mainActivityInstance by lazy {
        (activity as MainActivity?)
    }

    private val profileViewModel by viewModel<ProfileViewModel>()

    private var adapterClickListener: (GeneralEvents) -> Unit = {
        val bundle = Bundle()
        bundle.putSerializable(EVENT_KEY_WORD, it)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = profileViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        profileViewModel.getMyInfo()
    }

    private fun observeFields() {

    }

    private fun setEventsLocally() {

    }
}