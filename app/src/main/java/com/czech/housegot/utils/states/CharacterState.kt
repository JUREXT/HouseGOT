package com.czech.housegot.utils.states

import com.czech.housegot.models.DetailCharacters

sealed class CharacterState {
    data class Success(val data: DetailCharacters?) : CharacterState()
    data class Error(val message: String) : CharacterState()
    object Loading : CharacterState()
}