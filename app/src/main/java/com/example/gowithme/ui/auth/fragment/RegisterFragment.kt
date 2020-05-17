package com.example.gowithme.ui.auth.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.gowithme.MainActivity

import com.example.gowithme.R
import com.example.gowithme.databinding.FragmentConfirmPhoneBinding
import com.example.gowithme.databinding.FragmentRegisterBinding
import com.example.gowithme.ui.auth.viewmodel.AuthViewModel
import com.example.gowithme.util.sharedGraphViewModel
import kotlinx.android.synthetic.main.activity_main.*


class RegisterFragment : Fragment() {

    private val authViewModel: AuthViewModel by sharedGraphViewModel(R.id.registration)
    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        authViewModel.getCategories()
        (activity as MainActivity).toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
        with(binding) {
            categories.setOnClickListener {
                val direction = RegisterFragmentDirections.actionRegisterFragmentToFavCategorySelectionDialogFragment()
                findNavController().navigate(direction)
            }
            registerButton.setOnClickListener {
                authViewModel.firstName = firstNameInput.text.toString()
                authViewModel.lastName = lastNameInput.text.toString()
                authViewModel.email = emailInput.text.toString()
                authViewModel.password = passwordInput.text.toString()
                authViewModel.confPassword = passwordConfirmInput.text.toString()

                if (authViewModel.password != authViewModel.confPassword) {
                    passwordConfirmInputLayout.error = "Неверно"
                    return@setOnClickListener
                }

                if (authViewModel.firstName.isBlank()) {
                    firstNameInput.error = "Неверно"
                    return@setOnClickListener
                }

                if (authViewModel.lastName.isBlank()) {
                    lastNameInput.error = "Неверно"
                    return@setOnClickListener
                }

                authViewModel.register()
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        authViewModel.register.observe(viewLifecycleOwner, Observer {
            findNavController().navigateUp()
        })
    }


}
