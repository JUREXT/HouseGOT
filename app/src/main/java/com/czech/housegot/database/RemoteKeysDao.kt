package com.czech.housegot.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.czech.housegot.models.RemoteKeys

@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKeys>)

    @Query("Select * From remote_keys")
    suspend fun getRemoteKeys(): List<RemoteKeys?>

    @Query("Delete From remote_keys")
    suspend fun deleteRemoteKeys()

    @Query("Select created_at From remote_keys Order By created_at DESC LIMIT 1")
    suspend fun getCreationTime(): Long?
}