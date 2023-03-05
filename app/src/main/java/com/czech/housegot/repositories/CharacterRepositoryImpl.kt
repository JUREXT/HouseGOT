package com.czech.housegot.repositories

import com.czech.housegot.models.CharacterDetails
import com.czech.housegot.network.ApiService
import com.czech.housegot.utils.DataState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val ioDispatcher: CoroutineDispatcher
): CharacterRepository {

    override fun getCharDetails(charId: Int): Flow<DataState<CharacterDetails>> {
        return flow {
            emit(DataState.loading())

            val response = apiService.getCharacterDetail(charId = charId)
            val character = response.body()

            try {
                if (response.isSuccessful) {
                    emit(DataState.data(data = character))
                } else {
                    emit(DataState.error(message = response.message()))
                }
            } catch (e: IOException) {
                emit(
                    DataState.error(
                        message = e.message ?: "An error occurred"
                    )
                )
            } catch (e: HttpException) {
                emit(
                    DataState.error(
                        message = e.message ?: "An error occurred"
                    )
                )
            }
        }.flowOn(ioDispatcher)
    }
}