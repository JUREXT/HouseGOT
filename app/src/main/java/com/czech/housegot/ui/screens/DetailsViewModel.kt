package com.czech.housegot.ui.screens

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.czech.housegot.repositories.CharacterRepository
import com.czech.housegot.repositories.DetailsRepository
import com.czech.housegot.utils.DetailsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val detailsRepository: DetailsRepository,
    private val characterRepository: CharacterRepository
): ViewModel() {

    val houseId = savedStateHandle.get<Int>("house_id")
    val colorInt = savedStateHandle.get<Int>("color_int")

    val detailsState = MutableStateFlow<DetailsState?>(null)
    val founder = mutableStateOf<String?>(null)
    val lord = mutableStateOf<String?>(null)
    val heir = mutableStateOf<String?>(null)

//    init {
//        getDetails(houseId = houseId!!)
//    }

    fun getCharacters(founderId: Int?, lordId: Int?, heirId: Int?) {
        viewModelScope.launch {
            val founderResponse = characterRepository.getCharDetails(founderId)
            val lordResponse = characterRepository.getCharDetails(lordId)
            val heirResponse = characterRepository.getCharDetails(heirId)
            combine(
                founderResponse,
                lordResponse,
                heirResponse
            ) { founder, lord, heir ->
                listOf(
                    founder?.name,
                    lord?.name,
                    heir?.name
                )
            }.catch {
                detailsState.value = DetailsState.Error(message = it.message.toString())
            }.collect {
                founder.value = it[0]
                lord.value = it[1]
                heir.value = it[2]
            }

        }
    }

    fun getDetails(houseId: Int) {
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