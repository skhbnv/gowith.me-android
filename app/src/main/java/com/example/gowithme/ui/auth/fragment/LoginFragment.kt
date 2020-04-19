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
import com.example.gowithme.data.models.request.LoginRequest
import com.example.gowithme.databinding.FragmentLoginBinding
import com.example.gowithme.ui.auth.viewmodel.AuthViewModel
import com.example.gowithme.ui.auth.viewmodel.LoginUI
import com.example.gowithme.util.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val authViewModel: AuthViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            loginButton.setOnClickListener {
                val request = LoginRequest(
                    phone = phoneInput.text.toString(),
                    password = passwordInput.text.toString()
                )
                authViewModel.login(request)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        authViewModel.loginUI.observe(viewLifecycleOwner, Observer {
            when(it) {
                is LoginUI.PopUp -> {
                    findNavController().navigateUp()
                }
                is LoginUI.Error -> {
                    "Error".showToast(context)
                }
            }
        })
    }

}
