package com.example.gowithme.ui.create_new_event.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.gowithme.R
import com.example.gowithme.databinding.FragmentCreateNewEventBinding
import com.example.gowithme.ui.create_new_event.viewmodel.CreateEventUI
import com.example.gowithme.ui.create_new_event.viewmodel.CreateNewFragmentViewModel
import com.example.gowithme.ui.create_new_event.viewmodel.InputTypes
import com.example.gowithme.util.*
import kotlinx.android.synthetic.main.fragment_create_new_event.*
import kotlinx.android.synthetic.main.item_textview.view.*
import java.text.SimpleDateFormat
import java.util.*

class CreateNewEventFragment : Fragment() {


    private val createNewFragmentViewModel: CreateNewFragmentViewModel by sharedGraphViewModel(R.id.nav_create_new_event)
    private lateinit var binding: FragmentCreateNewEventBinding
    private val startCalendar by lazy { Calendar.getInstance() }
    private val endCalendar by lazy { Calendar.getInstance() }

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
        createNewFragmentViewModel.getCategories()
        with(binding) {
            startDateInput.setOnClickListener {
                startCalendar.showDateTimePicker(root.context) {
                    startDateInput.setText(SimpleDateFormat.getDateTimeInstance().format(it.time))
                    createNewFragmentViewModel.startDate = it.time.toIsoFormat()
                }
            }
            endDateInput.setOnClickListener {
                endCalendar.showDateTimePicker(root.context) {
                    endDateInput.setText(SimpleDateFormat.getDateTimeInstance().format(it.time))
                    createNewFragmentViewModel.endDate = it.time.toIsoFormat()
                }
            }
            selectCategoriesButton.setOnClickListener {
                val direction = CreateNewEventFragmentDirections.actionCreateNewEventFragmentToCategorySelectionDialogFragment()
                findNavController().navigate(direction)
            }
            addressInput.setOnClickListener {
                val direction = CreateNewEventFragmentDirections.actionNavCreateNewEventToMapAddressFragment()
                findNavController().navigate(direction)
            }
            isFreeSwitch.setOnCheckedChangeListener { _, isChecked ->
                when(isChecked) {
                    true -> {
                        priceInput.setText("Бесплатно")
                        priceInput.isEnabled = false
                    }
                    false -> {
                        priceInput.setText("")
                        priceInput.isEnabled = true
                    }
                }
            }
            createEventButton.setOnClickListener {
                with(createNewFragmentViewModel) {
                    title = titleInput.text.toString()
                    description = descriptionInput.text.toString()
                    price = priceInput.text.toString()
                }
                createNewFragmentViewModel.createEvent()
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        createNewFragmentViewModel.createEventUI.observe(viewLifecycleOwner, Observer {
            when(it) {
                is CreateEventUI.ValidationError -> {
                    Log.d("taaag", "ValidationError inputType ${it.inputType}")

                    validationError(it.inputType)
                }
            }
        })
        createNewFragmentViewModel.checkedCategoriesLD.observe(viewLifecycleOwner, Observer {
            with(binding.categoriesList) {
                removeAllViews()
                it.forEach { category ->
                    inflate(R.layout.item_textview).apply {
                        textView.text = category.name
                    }.also {
                        addView(it)
                    }
                }
            }
        })
        createNewFragmentViewModel.addressText.observe(viewLifecycleOwner, Observer {
            Log.d("taaag", "addressText observe $it")
            binding.addressInput.setText(it)
        })
    }

    private fun validationError(inputType: InputTypes) {
        when(inputType) {
            InputTypes.TITLE -> titleInput.error = "Invalid"
            InputTypes.DESCRIPTION -> descriptionInput.error = "Invalid"
            InputTypes.CATEGORY -> "Category is empty".showToast(context)
            InputTypes.ADDRESS -> addressInput.error = "Invalid"
            InputTypes.START -> startDateInput.error = "Invalid"
            InputTypes.END -> endDateInput.error = "Invalid"
            InputTypes.PRICE -> priceInput.error = "Invalid"
        }
    }

    companion object {
        private const val CATEGORY_SELECTION_FRAGMENT_TAG = "CategorySelectionDialogFragment"
    }
}