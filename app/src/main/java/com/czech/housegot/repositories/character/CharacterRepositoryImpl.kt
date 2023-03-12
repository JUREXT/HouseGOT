package com.czech.housegot.repositories.character

import com.czech.housegot.models.CharacterDetails
import com.czech.housegot.network.ApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val dispatcher: CoroutineDispatcher
): CharacterRepository {

    override fun getCharDetails(charId: Int?): Flow<CharacterDetails?> {
        return flow {
            if (charId != null) {
                try {
                    val response = apiService.getCharacterDetail(charId = charId)
                    val character = response.body()
                    if (response.isSuccessful) {
                        emit(character)
                    }
                } catch (e: IOException) {
                    emit(null)
                } catch (e: HttpException) {
                    emit(null)
                }
            } else {
                emit(null)
            }

        }.flowOn(dispatcher)
    }
}