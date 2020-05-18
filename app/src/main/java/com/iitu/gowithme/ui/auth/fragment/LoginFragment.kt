package com.iitu.gowithme.ui.auth.fragment


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

import com.iitu.gowithme.R
import com.iitu.gowithme.data.models.request.LoginRequest
import com.iitu.gowithme.databinding.FragmentLoginBinding
import com.iitu.gowithme.ui.auth.viewmodel.AuthViewModel
import com.iitu.gowithme.ui.auth.viewmodel.LoginUI
import com.iitu.gowithme.util.showToast
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val authViewModel: AuthViewModel by viewModel()
    private val safeArgs by navArgs<LoginFragmentArgs>()
    private val next by lazy { safeArgs.next }

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
        (activity as MainActivity).toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
        with(binding){
            loginButton.setOnClickListener {
                val request = LoginRequest(
                    phone = phoneInput.text.toString(),
                    password = passwordInput.text.toString()
                )
                authViewModel.login(request)
            }
            registerButton.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_registration)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        authViewModel.loginUI.observe(viewLifecycleOwner, Observer {
            when(it) {
                is LoginUI.Login -> {
                    findNavController().createDeepLink()
                        .setDestination(next)
                        .createPendingIntent()
                        .send(PendingIntent.FLAG_UPDATE_CURRENT)
                }
                is LoginUI.Error -> {
                    "Error".showToast(context)
                }
            }
        })
    }

}
