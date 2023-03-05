package com.czech.housegot.ui.screens

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.czech.housegot.models.CharacterCategory
import com.czech.housegot.repositories.CharacterRepository
import com.czech.housegot.repositories.DetailsRepository
import com.czech.housegot.utils.CharacterState
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

    private val houseId = savedStateHandle.get<Int>("house_id")

    val detailsState = MutableStateFlow<DetailsState?>(null)
    val characterState = MutableStateFlow<CharacterState?>(null)

    init {
        getDetails(houseId = houseId!!)
    }

    fun getCharacters(founderId: Int?, lordId: Int?, heirId: Int?) {
        viewModelScope.launch {
            if (founderId != null || lordId != null || heirId != null) {

            }
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
            }.onStart {
                characterState.value = CharacterState.Loading
            }.catch {
                characterState.value = CharacterState.Error(message = it.message.toString())
            }.collect {
                characterState.value = CharacterState.Success(data = it)
            }

        }
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