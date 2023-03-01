package com.czech.housegot.di

import androidx.room.Room
import com.czech.housegot.BaseApplication
import com.czech.housegot.database.HousesDao
import com.czech.housegot.database.HousesDatabase
import com.czech.housegot.database.RemoteKeysDao
import com.czech.housegot.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@[Module InstallIn(SingletonComponent::class)]
class DatabaseModule {

    @[Singleton Provides]
    fun provideDatabase(app: BaseApplication): HousesDatabase =
        Room
            .databaseBuilder(
                app,
                HousesDatabase::class.java,
                Constants.DATABASE
            )
            .build()

    @[Singleton Provides]
    fun provideHouseDao(db: HousesDatabase): HousesDao =
        db.housesDao()

    @[Singleton Provides]
    fun provideRemoteKeysDao(db: HousesDatabase): RemoteKeysDao =
        db.remoteKeysDao()
}