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

    @Query("SELECT * FROM remote_keys")
    suspend fun getRemoteKeys(): List<RemoteKeys?>

    @Query("DELETE FROM remote_keys")
    suspend fun deleteRemoteKeys()

    @Query("SELECT created_at FROM remote_keys ORDER BY created_at DESC LIMIT 1")
    suspend fun getCreationTime(): Long?
}