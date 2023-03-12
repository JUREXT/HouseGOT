package com.czech.housegot.di

import com.czech.housegot.database.HousesDao
import com.czech.housegot.database.RemoteKeysDao
import com.czech.housegot.network.ApiService
import com.czech.housegot.repositories.*
import com.czech.housegot.repositories.character.CharacterRepository
import com.czech.housegot.repositories.character.CharacterRepositoryImpl
import com.czech.housegot.repositories.detail.DetailsRepository
import com.czech.housegot.repositories.detail.DetailsRepositoryImpl
import com.czech.housegot.repositories.houses.HousesRepository
import com.czech.housegot.repositories.houses.HousesRepositoryImpl
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
        dispatcher: CoroutineDispatcher
    ): DetailsRepository =
        DetailsRepositoryImpl(
            apiService = apiService,
            dispatcher = dispatcher
        )

    @[Provides Singleton]
    fun provideCharacterRepository(
        apiService: ApiService,
        dispatcher: CoroutineDispatcher
    ): CharacterRepository =
        CharacterRepositoryImpl(
            apiService = apiService,
            dispatcher = dispatcher
        )
}