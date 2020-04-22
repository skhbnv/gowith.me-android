package com.example.gowithme.ui.create_new_event.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.gowithme.data.models.request.CreateEventRequest
import com.example.gowithme.data.models.response.CategoryResponse
import com.example.gowithme.data.network.event.IEventRepository
import com.example.gowithme.util.Result
import kotlinx.coroutines.launch

class CreateNewFragmentViewModel(private var repository: IEventRepository) : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _createEventUI = MutableLiveData<CreateEventUI>()
    val createEventUI: LiveData<CreateEventUI> get() = _createEventUI

    private val _categories = MutableLiveData<List<CategoryResponse>>()
    val categories: LiveData<List<CategoryResponse>> get() = _categories

    private val checkedCategoriesTrigger = MutableLiveData<Unit>()
    val checkedCategoriesLD: LiveData<List<CategoryResponse>> = Transformations.map(checkedCategoriesTrigger) {
        _categories.value?.filter { it.isChecked }
    }

    private val _addressText = MutableLiveData<String>()
    val  addressText: LiveData<String> get() = _addressText

    private var latitude = 0.0
    private var longitude = 0.0
    var price = ""
    var title = ""
    var description = ""
    var startDate = ""
    var endDate = ""

    fun setAddressText(text: String) {
        Log.d("taaag", "setAddressText $text")
        _addressText.value = text
    }


    fun setLatLng(latitude: Double, longitude: Double) {
        this.latitude = latitude
        this.longitude = longitude
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

    fun createEvent() {
        if (!checkRequestData()) {
            return
        }
        viewModelScope.launch {
            val createEventRequest = CreateEventRequest(
                categories = checkedCategoriesLD.value?.map { it.id } ?: emptyList(),
                title = title,
                description = description,
                latitude = latitude,
                longitude = longitude,
                start = startDate,
                end = endDate,
                price = price,
                images = emptyList()
            )
            when(val result = repository.createEvent(createEventRequest)) {
                is Result.Success -> {
                    Log.d("taaag", "createEvent Success")
                }
                is Result.Error -> {
                    Log.d("taaag", "createEvent Error")
                }
            }
        }
    }

    private fun checkRequestData(): Boolean {
        var isValid = true
        if (title.isBlank()) {
            _createEventUI.value = CreateEventUI.ValidationError(InputTypes.TITLE)
            isValid = false
        }
        if (description.isBlank()) {
            _createEventUI.value = CreateEventUI.ValidationError(InputTypes.DESCRIPTION)
            isValid = false
        }
        if (checkedCategoriesLD.value.isNullOrEmpty()) {
            _createEventUI.value = CreateEventUI.ValidationError(InputTypes.CATEGORY)
            isValid = false
        }
        if (latitude == 0.0 && longitude == 0.0) {
            _createEventUI.value = CreateEventUI.ValidationError(InputTypes.ADDRESS)
            isValid = false
        }
        if (price.isBlank()) {
            _createEventUI.value = CreateEventUI.ValidationError(InputTypes.PRICE)
            isValid = false
        }
        if (startDate.isBlank()) {
            _createEventUI.value = CreateEventUI.ValidationError(InputTypes.START)
            isValid = false
        }
        if (endDate.isBlank()) {
            _createEventUI.value = CreateEventUI.ValidationError(InputTypes.END)
            isValid = false
        }
        Log.d("taaag", "checkRequestData isValid $isValid")
        return isValid
    }

    override fun onCleared() {
        Log.d("taaag", "-----------------------------------------------------<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<")
        super.onCleared()
    }
}

sealed class CreateEventUI {

    data class ValidationError(val inputType: InputTypes) : CreateEventUI()

}

enum class InputTypes {
    TITLE, DESCRIPTION, ADDRESS, START, END, PRICE, CATEGORY
}