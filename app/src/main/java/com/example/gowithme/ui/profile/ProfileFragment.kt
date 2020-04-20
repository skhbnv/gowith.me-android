package com.example.gowithme.ui.profile

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.gowithme.MainActivity
import com.example.gowithme.MainViewModel
import com.example.gowithme.R
import com.example.gowithme.databinding.FragmentProfileBinding
import com.example.gowithme.responses.GeneralEvents
import com.example.gowithme.ui.adapters.EventsAdapter
import com.example.gowithme.util.EventsKeyWord.EVENT_KEY_WORD
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var adapter: EventsAdapter


    private val mainActivityInstance by lazy {
        (activity as MainActivity?)
    }
    private val mainViewModel by sharedViewModel<MainViewModel>()
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
        setupOptionMenu()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mainViewModel.loginState.observe(viewLifecycleOwner, Observer {
            if (!it) {
                val direction = ProfileFragmentDirections.actionGlobalLoginFragment(R.id.nav_profile)
                findNavController().navigate(direction)
            }
        })
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


    private fun observeFields() {

    }

    private fun setEventsLocally() {

    }
}