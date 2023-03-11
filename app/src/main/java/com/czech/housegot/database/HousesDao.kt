package com.czech.housegot.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.czech.housegot.models.Houses

@Dao
interface HousesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHouses(houses: List<Houses>)

    @Query("Select * From houses Order By page")
    fun getHouses(): PagingSource<Int, Houses>

    @Query("DELETE FROM houses")
    suspend fun deleteAllHouses()
}