package com.iitu.gowithme.ui.auth.fragment


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.iitu.gowithme.MainActivity

import com.iitu.gowithme.R
import com.iitu.gowithme.databinding.FragmentCheckPhoneBinding
import com.iitu.gowithme.databinding.FragmentConfirmPhoneBinding
import com.iitu.gowithme.ui.auth.viewmodel.AuthViewModel
import com.iitu.gowithme.ui.auth.viewmodel.LoginUI
import com.iitu.gowithme.util.sharedGraphViewModel
import com.iitu.gowithme.util.showAlert
import kotlinx.android.synthetic.main.activity_main.*

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
