package com.example.gowithme.ui.auth.fragment


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.gowithme.MainActivity

import com.example.gowithme.R
import com.example.gowithme.databinding.FragmentCheckPhoneBinding
import com.example.gowithme.databinding.FragmentConfirmPhoneBinding
import com.example.gowithme.ui.auth.viewmodel.AuthViewModel
import com.example.gowithme.ui.auth.viewmodel.LoginUI
import com.example.gowithme.util.sharedGraphViewModel
import com.example.gowithme.util.showAlert
import com.example.gowithme.util.showToast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_confirm_phone.*

class ConfirmPhoneFragment : Fragment() {

    private val authViewModel: AuthViewModel by sharedGraphViewModel(R.id.registration)
    private lateinit var binding: FragmentConfirmPhoneBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_confirm_phone, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
        binding.nextButton.setOnClickListener {
            val code = binding.codeInput.text.toString()
            authViewModel.confirmPhone(code)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        authViewModel.checkPhone.observe(viewLifecycleOwner, Observer {
//            it.content.showToast(context)
//        })
        authViewModel.confirmPhone.observe(viewLifecycleOwner, Observer {
            Log.d("taaag", "confirmPhone observe")
            val direction = ConfirmPhoneFragmentDirections.actionConfirmPhoneFragmentToRegisterFragment()
            findNavController().navigate(direction)
        })
        authViewModel.loginUI.observe(viewLifecycleOwner, Observer {
            when(it) {
                is LoginUI.Error -> {
                    showAlert(context, "Неверный код", ok = {})
                }
            }
        })
    }

}
