package com.czech.housegot.ui.screens.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.czech.housegot.models.DetailCharacters
import com.czech.housegot.repositories.character.CharacterRepository
import com.czech.housegot.repositories.detail.DetailsRepository
import com.czech.housegot.utils.states.DetailsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val detailsRepository: DetailsRepository,
    private val characterRepository: CharacterRepository,
    private val dispatcher: CoroutineDispatcher
): ViewModel() {

    private val houseId = savedStateHandle.get<Int>("house_id")
    val colorInt = savedStateHandle.get<Int>("color_int")

    private val _detailsState = MutableStateFlow<DetailsState?>(null)
    val detailsState: StateFlow<DetailsState?> = _detailsState

    private val _characterState = MutableStateFlow(DetailCharacters())
    val characterState: StateFlow<DetailCharacters> = _characterState

    init {
        getDetails(houseId = houseId!!)
    }

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
                DetailCharacters(
                    founder = founder?.name ?: "",
                    lord = lord?.name ?: "",
                    heir = heir?.name ?: ""
                )
            }.collect { character ->
                _characterState.value = character
            }
        }
    }

    fun getDetails(houseId: Int) {
        viewModelScope.launch(dispatcher) {
            detailsRepository.getHouseDetails(houseId = houseId).collect {
                when {
                    it.isLoading -> {
                        _detailsState.value = DetailsState.Loading
                    }
                    it.data == null -> {
                        _detailsState.value = DetailsState.Error(message = it.message.toString())
                    }
                    else -> {
                        _detailsState.value = DetailsState.Success(data = it.data)
                    }
                }
            }
        }
    }

}