package com.example.gowithme.ui.create_new_event.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.gowithme.R
import com.example.gowithme.databinding.FragmentCreateNewEventBinding
import com.example.gowithme.ui.create_new_event.viewmodel.CreateNewFragmentViewModel
import com.example.gowithme.util.inflate
import com.example.gowithme.util.showDateTimePicker
import kotlinx.android.synthetic.main.item_textview.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class CreateNewEventFragment : Fragment() {

    private val createNewFragmentViewModel: CreateNewFragmentViewModel by viewModel()
    private lateinit var binding: FragmentCreateNewEventBinding
    private val startCalendar by lazy { Calendar.getInstance() }
    private val endCalendar by lazy { Calendar.getInstance() }
    private val testList by lazy { arrayOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6", "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6") }

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
                }
            }
            endDateInput.setOnClickListener {
                endCalendar.showDateTimePicker(root.context) {
                    endDateInput.setText(SimpleDateFormat.getDateTimeInstance().format(it.time))
                }
            }
            selectCategoriesButton.setOnClickListener {
                val frag = CategorySelectionDialogFragment.newInstance()
                frag.show(childFragmentManager, "asd")
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
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
    }
}