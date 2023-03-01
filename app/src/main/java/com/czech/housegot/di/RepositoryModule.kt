package com.czech.housegot.di

import com.czech.housegot.database.HousesDao
import com.czech.housegot.database.HousesDatabase
import com.czech.housegot.database.RemoteKeysDao
import com.czech.housegot.network.ApiService
import com.czech.housegot.repositories.HousesRepository
import com.czech.housegot.repositories.HousesRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@[Module InstallIn(SingletonComponent::class)]
class RepositoryModule {

    @[Provides Singleton]
    fun provideHousesRepository(
        apiService: ApiService,
        housesDatabase: HousesDatabase,
        housesDao: HousesDao,
        remoteKeysDao: RemoteKeysDao
    ): HousesRepository =
        HousesRepositoryImpl(
            apiService = apiService,
            housesDatabase = housesDatabase,
            housesDao = housesDao,
            remoteKeysDao = remoteKeysDao
        )
}