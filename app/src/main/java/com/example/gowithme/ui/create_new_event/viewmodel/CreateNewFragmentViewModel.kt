package com.example.gowithme.ui.create_new_event.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.gowithme.data.models.response.CategoryResponse
import com.example.gowithme.data.network.event.IEventRepository
import com.example.gowithme.util.Result
import kotlinx.coroutines.launch

class CreateNewFragmentViewModel(private var repository: IEventRepository) : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _categories = MutableLiveData<List<CategoryResponse>>()
    val categories: LiveData<List<CategoryResponse>> get() = _categories

    private val checkedCategoriesTrigger = MutableLiveData<Unit>()
    val checkedCategoriesLD: LiveData<List<CategoryResponse>> = Transformations.map(checkedCategoriesTrigger) {
        _categories.value?.filter { it.isChecked }
    }


    fun getCategories() {
        _loading.value = true
        viewModelScope.launch {
            when (val result = repository.getEventCategories()) {
                is Result.Success -> {
                    _categories.value = result.data
                }
                is Result.Error -> {

                }
            }
        }
    }

    fun checkCategory(index: Int) {
        _categories.value?.get(index)?.apply{
            this.isChecked = !this.isChecked
        }
        checkedCategoriesTrigger.value = null
    }
}