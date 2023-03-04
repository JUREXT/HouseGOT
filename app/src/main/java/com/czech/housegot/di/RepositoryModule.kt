package com.czech.housegot.di

import com.czech.housegot.database.HousesDao
import com.czech.housegot.database.HousesDatabase
import com.czech.housegot.database.RemoteKeysDao
import com.czech.housegot.network.ApiService
import com.czech.housegot.repositories.DetailsRepository
import com.czech.housegot.repositories.DetailsRepositoryImpl
import com.czech.housegot.repositories.HousesRepository
import com.czech.housegot.repositories.HousesRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@[Module InstallIn(SingletonComponent::class)]
class RepositoryModule {

    @[Provides Singleton]
    fun provideHousesRepository(
        apiService: ApiService,
        housesDao: HousesDao,
        remoteKeysDao: RemoteKeysDao,
        ioDispatcher: CoroutineDispatcher
    ): HousesRepository =
        HousesRepositoryImpl(
            apiService = apiService,
            housesDao = housesDao,
            remoteKeysDao = remoteKeysDao,
            ioDispatcher = ioDispatcher
        )

    @[Provides Singleton]
    fun provideDetailsRepository(
        apiService: ApiService,
        ioDispatcher: CoroutineDispatcher
    ): DetailsRepository =
        DetailsRepositoryImpl(
            apiService = apiService,
            ioDispatcher = ioDispatcher
        )
}