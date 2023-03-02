package com.czech.housegot.ui.screens

import androidx.lifecycle.ViewModel
import com.czech.housegot.repositories.HousesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HousesViewModel @Inject constructor(
    private val housesRepository: HousesRepository
): ViewModel() {
}