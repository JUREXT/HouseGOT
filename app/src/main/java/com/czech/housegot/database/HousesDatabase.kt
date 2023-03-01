package com.czech.housegot.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.czech.housegot.models.Houses
import com.czech.housegot.models.RemoteKeys
import com.czech.housegot.utils.Converters

@Database(entities = [Houses::class, RemoteKeys::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class HousesDatabase: RoomDatabase() {

    abstract fun housesDao(): HousesDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}