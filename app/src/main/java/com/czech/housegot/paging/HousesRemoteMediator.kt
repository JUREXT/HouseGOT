package com.czech.housegot.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.czech.housegot.database.HousesDao
import com.czech.housegot.database.HousesDatabase
import com.czech.housegot.database.RemoteKeysDao
import com.czech.housegot.models.Houses
import com.czech.housegot.models.RemoteKeys
import com.czech.housegot.network.ApiService
import kotlinx.coroutines.flow.*
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class HousesRemoteMediator @Inject constructor(
    private val housesDao: HousesDao,
    private val remoteKeysDao: RemoteKeysDao,
    private val housesDatabase: HousesDatabase,
    private val apiService: ApiService
): RemoteMediator<Int, Houses>() {

    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(48, TimeUnit.HOURS)

        return if (System.currentTimeMillis() - (remoteKeysDao.getCreationTime() ?: 0) < cacheTimeout) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    private fun getRemoteKeyForFirstItem(state: PagingState<Int, Houses>): RemoteKeys? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { houses ->
            remoteKeysDao.getRemoteKeyById(houses.id)
        }
    }

    private fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, Houses>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                remoteKeysDao.getRemoteKeyById(id)
            }
        }
    }

    private fun getRemoteKeyForLastItem(state: PagingState<Int, Houses>): RemoteKeys? {
        return state.pages.lastOrNull {
                it.data.isNotEmpty()
            }?.data?.lastOrNull()?.let { houses ->
                remoteKeysDao.getRemoteKeyById(houses.id)
            }

    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Houses>
    ): MediatorResult {

    val page: Int = when (loadType) {
        LoadType.REFRESH -> {
            val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
            remoteKeys?.nextKey?.minus(1) ?: 1
        }
        LoadType.PREPEND -> {
            val remoteKeys = getRemoteKeyForFirstItem(state)
            // If remoteKeys is null, that means the refresh result is not in the database yet.
            val prevKey = remoteKeys?.prevKey
            prevKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
        }
        LoadType.APPEND -> {
            val remoteKeys = getRemoteKeyForLastItem(state)

            val nextKey = remoteKeys?.nextKey
            nextKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
        }
    }
        return try {
            val response = apiService.getHouses(page = page, pageSize = PAGE_SIZE)

            val houses = response.body()
            val endOfPaginationReached = houses!!.isEmpty()

            housesDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    remoteKeysDao.deleteRemoteKeys()
                    housesDao.deleteAllHouses()
                }
                val prevKey = if (page > 1) page - 1 else null
                val nextKey = if (endOfPaginationReached) null else page + 1
                val remoteKeys = houses.map {
                    RemoteKeys(id = it.id, prevKey = prevKey, currentPage = page, nextKey = nextKey)
                }

                remoteKeysDao.insertAll(remoteKeys)
                housesDao.insertHouses(houses.onEachIndexed { _, house -> house.page = page })
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (error: IOException) {
            MediatorResult.Error(error)
        } catch (error: HttpException) {
            MediatorResult.Error(error)
        }
    }

    companion object {
        const val PAGE_SIZE = 20
    }
}