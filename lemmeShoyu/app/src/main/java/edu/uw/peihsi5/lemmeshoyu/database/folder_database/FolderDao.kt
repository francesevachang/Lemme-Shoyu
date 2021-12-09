package edu.uw.peihsi5.lemmeshoyu.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.*
import androidx.room.Query

@Dao
interface FolderDao {
    @Insert(onConflict = IGNORE)
    suspend fun insert(folder: Folder)

    @Query("DELETE FROM folderTable")
    suspend fun deleteAllFolders()

    @Delete
    suspend fun delete(folder: Folder)

    @Query("SELECT * FROM folderTable")
    fun getAllFolders(): LiveData<List<Folder>>
}