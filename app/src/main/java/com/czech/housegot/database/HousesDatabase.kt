package com.czech.housegot.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.czech.housegot.models.Houses
import com.czech.housegot.models.RemoteKeys

@Database(entities = [Houses::class, RemoteKeys::class], version = 1, exportSchema = false)
abstract class HousesDatabase: RoomDatabase() {

    abstract fun housesDao(): HousesDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}