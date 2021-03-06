package com.iitu.gowithme.ui.auth.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.iitu.gowithme.R
import com.iitu.gowithme.databinding.DialogFragmentCategorySelectionBinding
import com.iitu.gowithme.ui.auth.viewmodel.AuthViewModel
import com.iitu.gowithme.ui.create_new_event.adapter.CategoryCheckboxRecyclerAdapter
import com.iitu.gowithme.util.sharedGraphViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class FavCategorySelectionDialogFragment : BottomSheetDialogFragment() {

    private lateinit var binding: DialogFragmentCategorySelectionBinding
    private val authViewModel: AuthViewModel by sharedGraphViewModel(R.id.registration)

    private val adapter by lazy {
        CategoryCheckboxRecyclerAdapter {
            authViewModel.checkCategory(it)
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

        authViewModel.categories.observe(viewLifecycleOwner, Observer {
            adapter.setCategories(it)
        })
    }

}
