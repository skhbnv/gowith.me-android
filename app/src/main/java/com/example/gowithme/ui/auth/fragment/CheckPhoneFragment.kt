package com.example.gowithme.ui.auth.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController

import com.example.gowithme.R
import com.example.gowithme.data.models.request.CheckPhoneRequest
import com.example.gowithme.databinding.FragmentCheckPhoneBinding
import com.example.gowithme.ui.auth.viewmodel.AuthViewModel
import com.example.gowithme.ui.create_new_event.viewmodel.CreateNewEventViewModel
import com.example.gowithme.util.sharedGraphViewModel

class CheckPhoneFragment : Fragment() {

    private val authViewModel: AuthViewModel by sharedGraphViewModel(R.id.registration)
    private lateinit var binding: FragmentCheckPhoneBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_check_phone, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            nextButton.setOnClickListener {
                val phone = phoneInput.text.toString()
                authViewModel.checkPhone(phone)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        authViewModel.checkPhone.observe(viewLifecycleOwner, Observer {
            val direction = CheckPhoneFragmentDirections.actionCheckPhoneFragmentToConfirmPhoneFragment()
            findNavController().navigate(direction)
        })
    }


}