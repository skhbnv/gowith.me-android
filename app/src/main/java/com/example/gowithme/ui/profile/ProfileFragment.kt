package com.example.gowithme.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.gowithme.R
import com.example.gowithme.databinding.FragmentProfileBinding
import com.example.gowithme.network.ApiRepository
import com.example.gowithme.ui.dashboard.DashboardViewModel

class ProfileFragment : Fragment() {
    lateinit var profileBinding: FragmentProfileBinding

    private val profileViewModel by lazy {
        ViewModelProviders.of(activity!!, ProfileViewModel.ProfileFactory(ApiRepository()))
            .get(ProfileViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        return profileBinding.root
    }
}