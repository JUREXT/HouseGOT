package com.czech.housegot.utils

import com.czech.housegot.models.CharacterDetails
import com.czech.housegot.models.Houses

sealed class CharacterState {
    data class Success(val data: List<String?>) : CharacterState()
    data class Error(val message: String) : CharacterState()
    object Loading : CharacterState()
}