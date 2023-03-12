package com.czech.housegot.repositories.character

import com.czech.housegot.models.CharacterDetails
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {

    fun getCharDetails(charId: Int?): Flow<CharacterDetails?>
}