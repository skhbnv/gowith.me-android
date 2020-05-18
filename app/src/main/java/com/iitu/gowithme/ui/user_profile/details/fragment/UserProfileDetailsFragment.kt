package com.iitu.gowithme.ui.user_profile.details.fragment

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
import com.iitu.gowithme.BuildConfig
import com.iitu.gowithme.MainActivity
import com.iitu.gowithme.MainViewModel

import com.iitu.gowithme.R
import com.iitu.gowithme.data.network.event_list.EventListType
import com.iitu.gowithme.data.network.user.UserListType
import com.iitu.gowithme.data.network.user.UserListTypeEnum
import com.iitu.gowithme.databinding.FragmentUserProfileDetailsBinding
import com.iitu.gowithme.ui.user_profile.viewmodel.ProfileDetailsUI
import com.iitu.gowithme.ui.user_profile.viewmodel.UserProfileViewModel
import com.iitu.gowithme.util.showAlert
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_user_profile_details.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.Exception

class UserProfileDetailsFragment : Fragment() {

    private lateinit var binding: FragmentUserProfileDetailsBinding
    private val userProfileViewModel by viewModel<UserProfileViewModel>()
    private val safeArgs by navArgs<UserProfileDetailsFragmentArgs>()
    private val userId by lazy { safeArgs.userId }
    private val mainViewModel by viewModel<MainViewModel>()

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
        (activity as MainActivity).toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
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
            followers.setOnClickListener {
                val direction = UserProfileDetailsFragmentDirections
                    .actionUserProfileDetailsFragmentToUserListFragment(UserListType(UserListTypeEnum.USER_FOLLOWERS, userId))
                findNavController().navigate(direction)
            }
            following.setOnClickListener {
                val direction = UserProfileDetailsFragmentDirections
                    .actionUserProfileDetailsFragmentToUserListFragment(UserListType(
                        UserListTypeEnum.USER_FOLLOWING, userId))
                findNavController().navigate(direction)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        userProfileViewModel.profileDetailsUI.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ProfileDetailsUI.ProfileDetailsLoadError -> {
                    showAlert(context, message = getString(R.string.text_loading_error), ok = {})
                }
            }
        })

        userProfileViewModel.profileInfo.observe(viewLifecycleOwner, Observer {
            it.images.firstOrNull()?.image?.let {
                Glide.with(binding.root.context)
                    .load(BuildConfig.BASE_URL + it.substring(1))
                    .into(avatarImage)
            }
        })
        mainViewModel.loginState.observe(viewLifecycleOwner, Observer {
            with(binding) {
                if(it) {
                    followButton.setOnClickListener {
                        userProfileViewModel.subscribeOrUnSubscribe(userId)
                    }
                } else {
                    followButton.setOnClickListener {
                        showAlert(context, message = getString(R.string.text_pls_auth), ok = {})
                    }
                }
            }
        })
    }

}
