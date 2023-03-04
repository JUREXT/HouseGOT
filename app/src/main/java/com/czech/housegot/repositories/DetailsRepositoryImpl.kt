package com.czech.housegot.repositories

import com.czech.housegot.models.Houses
import com.czech.housegot.network.ApiService
import com.czech.housegot.utils.DataState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class DetailsRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val ioDispatcher: CoroutineDispatcher
): DetailsRepository {

    override fun getHouseDetails(houseId: Int): Flow<DataState<Houses>> {
        return flow {
            emit(DataState.loading())

            val response = apiService.getHouseDetail(houseId = houseId)
            val houses = response.body()

            try {
                if (response.isSuccessful) {
                    emit(DataState.data(data = houses))
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