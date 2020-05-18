package com.iitu.gowithme.ui.profile.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.iitu.gowithme.BuildConfig
import com.iitu.gowithme.MainActivity
import com.iitu.gowithme.MainViewModel
import com.iitu.gowithme.R
import com.iitu.gowithme.data.network.event_list.EventListType
import com.iitu.gowithme.data.network.user.UserListType
import com.iitu.gowithme.data.network.user.UserListTypeEnum
import com.iitu.gowithme.databinding.FragmentProfileBinding
import com.iitu.gowithme.ui.adapters.EventsAdapter
import com.iitu.gowithme.ui.profile.viewmodel.ProfileViewModel
import com.iitu.gowithme.ui.profile.adapter.ViewedEventsRecyclerAdapter
import com.iitu.gowithme.util.getFileName
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class ProfileFragment : Fragment() {

    companion object {
        private const val GALLERY_REQUEST_CODE = 1
    }

    private lateinit var binding: FragmentProfileBinding
    private lateinit var adapter: EventsAdapter
    
    private val mainActivityInstance by lazy {
        (activity as MainActivity?)
    }
    private val viewedEventsRecyclerAdapter by lazy { ViewedEventsRecyclerAdapter() }
    private val mainViewModel by sharedViewModel<MainViewModel>()
    private val profileViewModel by viewModel<ProfileViewModel>()

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
        setupOptionMenu()

        with(binding) {
            viewEventsRecycler.adapter = viewedEventsRecyclerAdapter
            viewedEventsRecyclerAdapter.onItemClicked = {
                val direction = ProfileFragmentDirections.actionNavProfileToEventPageFragment(it)
                findNavController().navigate(direction)
            }
            allViewedEvents.setOnClickListener {
                val direction = ProfileFragmentDirections.actionNavProfileToEventListFragment(
                    EventListType.VIEWED_EVENTS)
                findNavController().navigate(direction)
            }
            myFollowers.setOnClickListener {
                val direction = ProfileFragmentDirections.actionNavProfileToUserListFragment(
                    UserListType(
                        UserListTypeEnum.MY_FOLLOWERS
                    )
                )
                findNavController().navigate(direction)
            }
            myFollowing.setOnClickListener {
                val direction = ProfileFragmentDirections.actionNavProfileToUserListFragment(
                    UserListType(
                        UserListTypeEnum.MY_FOLLOWING
                    )
                )
                findNavController().navigate(direction)
            }
            myEvents.setOnClickListener {
                val direction = ProfileFragmentDirections.actionNavProfileToEventListFragment(EventListType.MY_EVENTS)
                findNavController().navigate(direction)
            }
            savedEvents.setOnClickListener {
                val direction = ProfileFragmentDirections.actionNavProfileToEventListFragment(EventListType.SAVED_EVENTS)
                findNavController().navigate(direction)
            }
            button.setOnClickListener {
                val galleryIntent = Intent(Intent.ACTION_PICK,  MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(galleryIntent,
                    GALLERY_REQUEST_CODE
                )
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == GALLERY_REQUEST_CODE) {
            data?.data?.let { uri ->
                val parcelFileDescriptor =
                    requireContext().contentResolver.openFileDescriptor(uri, "r", null) ?: return
                val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
                val file = File(requireContext().cacheDir, requireContext().contentResolver.getFileName(uri))
                val outputStream = FileOutputStream(file)
                inputStream.copyTo(outputStream)
                inputStream.close()
                outputStream.close()
                profileViewModel.uploadImage(file)
            }
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mainViewModel.loginState.observe(viewLifecycleOwner, Observer {
            if (!it) {
                val direction =
                    ProfileFragmentDirections.actionGlobalLoginFragment(
                        R.id.nav_profile
                    )
                findNavController().navigate(direction)
            }
        })

        profileViewModel.profileInfo.observe(viewLifecycleOwner, Observer {
            it.images.firstOrNull()?.let {
                with(binding.avatarImage) {
                    this.contentDescription = it.description
                    Glide.with(context).load(BuildConfig.BASE_URL + it.image.substring(1)).into(this)
                }
            }
        })

        profileViewModel.profileImage.observe(viewLifecycleOwner, Observer {
            with(binding.avatarImage) {
                Glide.with(context).load(it).into(this)
            }
        })

        profileViewModel.viewedEvents.observe(viewLifecycleOwner, Observer(viewedEventsRecyclerAdapter::setEventList))
    }

    private fun setupOptionMenu() {
        setHasOptionsMenu(true)
        activity?.toolbar?.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.logout -> {
                    mainViewModel.logout()
                    activity?.recreate()
                    true
                }
                else -> {
                    super.onOptionsItemSelected(it)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.profile_toolbar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}