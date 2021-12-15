/**
 * Pacy Wu: I wrote the FolderRepository as an entrance of inserting, deleting, and retrieving data
 * in the folder database.
 **/

package edu.uw.peihsi5.lemmeshoyu.repositories

import androidx.lifecycle.LiveData
import edu.uw.peihsi5.lemmeshoyu.database.Folder
import edu.uw.peihsi5.lemmeshoyu.database.FolderDao

class FolderRepository(private val folderDao: FolderDao) {

    val allFolders: LiveData<List<Folder>> = folderDao.getAllFolders()

    /** Insert the given folder to the database **/
    suspend fun insert(folder: Folder) {
        folderDao.insert(folder)
    }

    /** Delete the given folder in the database **/
    suspend fun delete(folder: Folder) {
        folderDao.delete(folder)
    }

    /** Delete all folders in the database **/
    suspend fun deleteAllFolders() {
        folderDao.deleteAllFolders()
    }

}