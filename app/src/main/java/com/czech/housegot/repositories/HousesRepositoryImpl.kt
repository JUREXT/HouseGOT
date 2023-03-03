package com.czech.housegot.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.czech.housegot.database.HousesDao
import com.czech.housegot.database.HousesDatabase
import com.czech.housegot.database.RemoteKeysDao
import com.czech.housegot.models.Houses
import com.czech.housegot.network.ApiService
import com.czech.housegot.paging.HousesRemoteMediator
import com.czech.housegot.utils.Constants
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class HousesRepositoryImpl @Inject constructor(
    private val housesDao: HousesDao,
    private val remoteKeysDao: RemoteKeysDao,
    private val apiService: ApiService,
    private val ioDispatcher: CoroutineDispatcher
): HousesRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getPagedHouses(): Flow<PagingData<Houses>> {
        return Pager(
            initialKey = 1,
            config = PagingConfig(
                pageSize = Constants.PAGE_SIZE,
                prefetchDistance = Constants.PAGE_SIZE / 4,
                initialLoadSize = Constants.PAGE_SIZE
            ),
            pagingSourceFactory = {
                housesDao.getHouses()
            },
            remoteMediator = HousesRemoteMediator(
                housesDao = housesDao,
                remoteKeysDao = remoteKeysDao,
                apiService = apiService,
                ioDispatcher = ioDispatcher
            )
        ).flow
    }
}