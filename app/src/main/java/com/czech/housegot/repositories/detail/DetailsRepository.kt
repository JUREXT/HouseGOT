package com.czech.housegot.repositories.detail

import com.czech.housegot.models.Houses
import com.czech.housegot.utils.states.DataState
import kotlinx.coroutines.flow.Flow

interface DetailsRepository {

    fun getHouseDetails(houseId: Int): Flow<DataState<Houses>>
}