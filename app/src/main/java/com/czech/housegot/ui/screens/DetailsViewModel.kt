package com.czech.housegot.ui.screens

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.czech.housegot.repositories.DetailsRepository
import com.czech.housegot.utils.DetailsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val detailsRepository: DetailsRepository
): ViewModel() {

    private val houseId = savedStateHandle.get<Int>("house_id")

    val detailsState = MutableStateFlow<DetailsState?>(null)

    init {
        getDetails(houseId = houseId!!)
    }

    private fun getDetails(houseId: Int) {
        viewModelScope.launch {
            detailsRepository.getHouseDetails(houseId = houseId).collect {
                when {
                    it.isLoading -> {
                        detailsState.value = DetailsState.Loading
                    }
                    it.data == null -> {
                        detailsState.value = DetailsState.Error(message = it.message.toString())
                    }
                    else -> {
                        detailsState.value = DetailsState.Success(data = it.data)
                    }
                }
            }
        }
    }

}