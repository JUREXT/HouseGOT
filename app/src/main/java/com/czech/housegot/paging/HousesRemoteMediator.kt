package com.czech.housegot.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.czech.housegot.database.HousesDao
import com.czech.housegot.database.HousesDatabase
import com.czech.housegot.database.RemoteKeysDao
import com.czech.housegot.models.Houses
import com.czech.housegot.models.RemoteKeys
import com.czech.housegot.network.ApiService
import com.czech.housegot.utils.Constants.Companion.PAGE_SIZE
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class HousesRemoteMediator @Inject constructor(
    private val housesDao: HousesDao,
    private val remoteKeysDao: RemoteKeysDao,
    private val apiService: ApiService,
    private val ioDispatcher: CoroutineDispatcher
): RemoteMediator<Int, Houses>() {

    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(48, TimeUnit.HOURS)

        return withContext(ioDispatcher) {
            if (System.currentTimeMillis() - (remoteKeysDao.getCreationTime() ?: 0) < cacheTimeout) {
                InitializeAction.SKIP_INITIAL_REFRESH
            } else {
                InitializeAction.LAUNCH_INITIAL_REFRESH
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(): RemoteKeys? {
        return withContext(ioDispatcher) {
            remoteKeysDao.getRemoteKeys().firstOrNull()
        }
    }

    private suspend fun getRemoteKeyForLastItem(): RemoteKeys? {
        return withContext(ioDispatcher) {
            remoteKeysDao.getRemoteKeys().lastOrNull()
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Houses>
    ): MediatorResult {

    val page: Int = when (loadType) {
        LoadType.REFRESH -> {
            val remoteKeys = getRemoteKeyForFirstItem()
            remoteKeys?.nextKey?.minus(1) ?: 1
        }
        LoadType.APPEND -> {
            val remoteKeys = getRemoteKeyForLastItem()

            val nextKey = remoteKeys?.nextKey
            nextKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
        }
        LoadType.PREPEND -> {
            return MediatorResult.Success(endOfPaginationReached = true)
        }

    }
        return withContext(ioDispatcher) {
            try {
                val houses = page.let { apiService.getHouses(page = it, pageSize = PAGE_SIZE) }

                val endOfPaginationReached = houses.isEmpty()

                if (loadType == LoadType.REFRESH) {
                    remoteKeysDao.deleteRemoteKeys()
                    housesDao.deleteAllHouses()
                }
                val prevKey = if (page > 1) page - 1 else null
                val nextKey = if (page >= 22) null else page + 1
                val remoteKeys = houses.map {
                    RemoteKeys(
                        id = it.id,
                        prevKey = prevKey,
                        currentPage = page,
                        nextKey = nextKey
                    )
                }

                remoteKeysDao.insertAll(remoteKeys)
                housesDao.insertHouses(houses.onEach {house -> house.page = page })

                MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
            } catch (error: IOException) {
                MediatorResult.Error(error)
            } catch (error: HttpException) {
                MediatorResult.Error(error)
            }
        }

    }
}