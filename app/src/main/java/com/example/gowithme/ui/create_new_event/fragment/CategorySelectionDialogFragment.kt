package com.example.gowithme.ui.create_new_event.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.gowithme.R
import com.example.gowithme.databinding.DialogFragmentCategorySelectionBinding
import com.example.gowithme.ui.create_new_event.adapter.CategoryCheckboxRecyclerAdapter
import com.example.gowithme.ui.create_new_event.viewmodel.CreateNewFragmentViewModel
import com.example.gowithme.util.inflate
import com.example.gowithme.util.sharedGraphViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.item_checkbox.view.*
import org.koin.androidx.viewmodel.ext.android.getViewModel


class CategorySelectionDialogFragment : BottomSheetDialogFragment() {

    private lateinit var binding: DialogFragmentCategorySelectionBinding
    private val createNewFragmentViewModel: CreateNewFragmentViewModel by sharedGraphViewModel(R.id.nav_create_new_event)

    private val adapter by lazy {
        CategoryCheckboxRecyclerAdapter {
            createNewFragmentViewModel.checkCategory(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_fragment_category_selection, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            title = "Выбирите категории"
            categoriesList.adapter = adapter
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        createNewFragmentViewModel.categories.observe(viewLifecycleOwner, Observer {
            adapter.setCategories(it)
        })
    }

    companion object {
        fun newInstance() = CategorySelectionDialogFragment()
    }
}
