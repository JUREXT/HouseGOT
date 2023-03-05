package com.czech.housegot.utils

import com.czech.housegot.models.Houses

sealed class DetailsState {
    data class Success(val data: Houses?) : DetailsState()
    data class Error(val message: String) : DetailsState()
    object Loading : DetailsState()
}