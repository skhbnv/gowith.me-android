package com.example.gowithme.ui.create_new_event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.gowithme.MainActivity
import com.example.gowithme.R
import com.example.gowithme.databinding.FragmentCreateNewEventBinding
import com.example.gowithme.util.showToast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateNewEventFragment : Fragment() {
    private val dashboardViewModel by viewModel<CreateNewFragmentViewModel>()
    private val mainActivityInstance by lazy {
        (activity as MainActivity?)
    }
    private lateinit var binding: FragmentCreateNewEventBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_new_event, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            startDateInput.setOnClickListener {

            }
        }
    }
}