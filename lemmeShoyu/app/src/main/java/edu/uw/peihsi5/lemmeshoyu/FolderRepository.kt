package edu.uw.peihsi5.lemmeshoyu

import androidx.lifecycle.LiveData
import edu.uw.peihsi5.lemmeshoyu.database.Folder
import edu.uw.peihsi5.lemmeshoyu.database.FolderDao

class FolderRepository(private val folderDao: FolderDao) {

    val allFolders: LiveData<List<Folder>> = folderDao.getAllFolders()


    suspend fun insert(folder: Folder) {
        folderDao.insert(folder)
    }

    suspend fun delete(folder: Folder) {
        folderDao.delete(folder)
    }

    suspend fun deleteAllFolders() {
        folderDao.deleteAllFolders()
    }

}