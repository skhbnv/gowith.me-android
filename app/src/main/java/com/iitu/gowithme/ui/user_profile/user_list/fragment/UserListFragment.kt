package com.iitu.gowithme.ui.user_profile.user_list.fragment


import android.app.PendingIntent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.iitu.gowithme.MainActivity
import com.iitu.gowithme.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

import com.iitu.gowithme.R
import com.iitu.gowithme.data.network.user.UserListTypeEnum
import com.iitu.gowithme.databinding.FragmentUserListBinding
import com.iitu.gowithme.ui.user_profile.user_list.adapter.UserListPagedAdapter
import com.iitu.gowithme.ui.user_profile.viewmodel.ProfileDetailsUI
import com.iitu.gowithme.ui.user_profile.viewmodel.UserProfileViewModel
import kotlinx.android.synthetic.main.activity_main.*

class UserListFragment : Fragment() {

    private lateinit var binding: FragmentUserListBinding
    private val onProfileClicked : (Int) -> Unit = { userId ->
        val direction = UserListFragmentDirections.actionUserListFragmentToUserProfileDetailsFragment(userId)
        findNavController().navigate(direction)

    }
    private val userListPagedAdapter by lazy { UserListPagedAdapter(listType) }
    private val viewModel by viewModel<UserProfileViewModel>()
    private val safeArgs by navArgs<UserListFragmentArgs>()
    private val listType by lazy { safeArgs.listType }
    private val mainViewModel by viewModel<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_list, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getEventSubscribers(listType)
        (activity as MainActivity).toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
        with(binding) {
            recycler.adapter = userListPagedAdapter
        }


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.usersList.observe(viewLifecycleOwner, Observer(userListPagedAdapter::submitList))
        viewModel.profileDetailsUI.observe(viewLifecycleOwner, Observer {
            when(it) {
                is ProfileDetailsUI.RemoveUserSuccess -> {
                    viewModel.getEventSubscribers(listType)
                }
            }
        })

        mainViewModel.loginState.observe(viewLifecycleOwner, Observer {
            userListPagedAdapter.onProfileClicked = { userId ->
                if (it) {
                    if (mainViewModel.userInfo?.id == userId) {
                        findNavController().createDeepLink()
                            .setDestination(R.id.nav_profile)
                            .createPendingIntent()
                            .send(PendingIntent.FLAG_UPDATE_CURRENT)
                    } else {
                        onProfileClicked(userId)
                    }
                } else {
                    onProfileClicked(userId)
                }
            }
            userListPagedAdapter.onRemoveClicked = { userId ->
                if (listType.type == UserListTypeEnum.EVENT_SUBSCRIBERS) {
                    viewModel.removeUser(listType.id, userId)
                }
            }
        })

    }

}
