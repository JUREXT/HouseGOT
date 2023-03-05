package com.czech.housegot.repositories

import com.czech.housegot.models.CharacterDetails
import com.czech.housegot.utils.DataState
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {

    fun getCharDetails(charId: Int?): Flow<CharacterDetails?>
}