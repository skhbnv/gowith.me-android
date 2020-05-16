package com.example.gowithme.ui.user_profile.user_list.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.viewModel

import com.example.gowithme.R
import com.example.gowithme.databinding.FragmentUserListBinding
import com.example.gowithme.ui.user_profile.user_list.adapter.UserListPagedAdapter
import com.example.gowithme.ui.user_profile.viewmodel.UserProfileViewModel
import java.lang.Exception

class UserListFragment : Fragment() {

    private lateinit var binding: FragmentUserListBinding
    private val onProfileClicked : (Int) -> Unit = { userId ->
        val direction = UserListFragmentDirections.actionUserListFragmentToUserProfileDetailsFragment(userId)
        try {
            findNavController().navigate(direction)
        } catch (e: Exception) {
            e.stackTrace
        }
    }
    private val userListPagedAdapter by lazy { UserListPagedAdapter(onProfileClicked) }
    private val viewModel by viewModel<UserProfileViewModel>()
    private val safeArgs by navArgs<UserListFragmentArgs>()
    private val eventId by lazy { safeArgs.eventId }

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
        viewModel.getEventSubscribers(eventId)

        with(binding) {
            recycler.adapter = userListPagedAdapter
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.usersList.observe(viewLifecycleOwner, Observer(userListPagedAdapter::submitList))
    }

}
