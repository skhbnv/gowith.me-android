package com.example.gowithme.ui.user_profile.details.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.gowithme.BuildConfig

import com.example.gowithme.R
import com.example.gowithme.data.network.event_list.EventListType
import com.example.gowithme.databinding.FragmentUserProfileDetailsBinding
import com.example.gowithme.ui.user_profile.viewmodel.UserProfileViewModel
import kotlinx.android.synthetic.main.fragment_user_profile_details.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.Exception

class UserProfileDetailsFragment : Fragment() {

    private lateinit var binding: FragmentUserProfileDetailsBinding
    private val userProfileViewModel by viewModel<UserProfileViewModel>()
    private val safeArgs by navArgs<UserProfileDetailsFragmentArgs>()
    private val userId by lazy { safeArgs.userId }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_user_profile_details,
            container,
            false
        )
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userProfileViewModel.getUserProfileInfo(userId)
        with(binding) {
            viewModel = userProfileViewModel

            events.setOnClickListener {
                val direction = UserProfileDetailsFragmentDirections
                    .actionUserProfileDetailsFragmentToEventListFragment(EventListType.USER_EVENTS, userId)
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

        userProfileViewModel.profileInfo.observe(viewLifecycleOwner, Observer {
            it.images.firstOrNull()?.image?.let {
                Glide.with(binding.root.context)
                    .load(BuildConfig.BASE_URL + it.substring(1))
                    .into(avatarImage)
            }
        })
    }

}
