package com.czech.housegot.repositories

import com.czech.housegot.models.CharacterDetails
import com.czech.housegot.network.ApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val ioDispatcher: CoroutineDispatcher
): CharacterRepository {

    override fun getCharDetails(charId: Int?): Flow<CharacterDetails?> {
        return flow {
            if (charId != null) {
                apiService.getCharacterDetail(charId = charId).body()?.let {
                    emit(it)
                }
            } else {
                emit(null)
            }

        }.flowOn(ioDispatcher)
    }
}