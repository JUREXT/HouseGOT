package com.czech.housegot

import android.content.Context
import androidx.paging.*
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.czech.housegot.database.HousesDao
import com.czech.housegot.database.HousesDatabase
import com.czech.housegot.database.RemoteKeysDao
import com.czech.housegot.models.Houses
import com.czech.housegot.models.RemoteKeys
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HouseDatabaseTest {

    private lateinit var db: HousesDatabase
    private lateinit var housesDao: HousesDao
    private lateinit var keysDao: RemoteKeysDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, HousesDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        housesDao = db.housesDao()
        keysDao = db.remoteKeysDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun houseDaoTest() = runBlocking {
        val houses = listOf(
            Houses(
                id = 1,
                url = "",
                name = "",
                region = "",
                coatOfArms = "",
                titles = listOf(),
                words = "",
                seats = listOf(),
                currentLord = "",
                heir = "",
                overlord = "",
                founded = "",
                founder = "",
                diedOut = "",
                ancestralWeapons = listOf(),
                cadetBranches = listOf(),
                swornMembers = listOf(),
                page = 1
            )
        )
        housesDao.insertHouses(houses)

        val pagedHouses = housesDao.getHouses().load(PagingSource.LoadParams.Refresh(key = null, loadSize = 20, placeholdersEnabled = false))

        Assert.assertEquals((pagedHouses as PagingSource.LoadResult.Page).data, houses)
    }

    @Test
    fun remoteKeysDaoTest() = runBlocking {
        val keys = listOf(
            RemoteKeys(
                id = 1,
                prevKey = 0,
                currentPage = 1,
                nextKey = 2
            ),
            RemoteKeys(
                id = 2,
                prevKey = 1,
                currentPage = 2,
                nextKey = 3
            )
        )
        keysDao.insertAll(keys)

        val getKeys = keysDao.getRemoteKeys()

        Assert.assertEquals(getKeys.size, 2)
        Assert.assertEquals(getKeys[0]?.currentPage, 1)
    }
}