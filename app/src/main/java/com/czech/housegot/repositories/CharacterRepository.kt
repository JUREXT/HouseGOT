package com.czech.housegot.repositories

import com.czech.housegot.models.CharacterDetails
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {

    fun getCharDetails(charId: Int?): Flow<CharacterDetails?>
}