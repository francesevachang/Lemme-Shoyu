package edu.uw.peihsi5.lemmeshoyu.viewmodels

import android.app.Application
import android.database.sqlite.SQLiteException
import android.util.Log
import androidx.lifecycle.*
import edu.uw.peihsi5.lemmeshoyu.repositories.FolderRepository
import edu.uw.peihsi5.lemmeshoyu.database.Folder
import edu.uw.peihsi5.lemmeshoyu.database.FolderDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = ".FolderViewModel"

class FolderViewModel(application: Application): AndroidViewModel(application) {
     var allFolders: LiveData<List<Folder>>? = null
     var repository: FolderRepository? = null

    init {
        val folderDao = FolderDatabase.getDatabase(application)?.getFoldersDao()
        if (folderDao != null) {
            repository = FolderRepository(folderDao)
            allFolders = repository!!.allFolders
        }
    }

    fun insertFolder(folder: Folder, exceptionHandler: () -> Unit) {
        viewModelScope.launch (Dispatchers.IO) {
            try{
                repository?.insert(folder)
            } catch(e: SQLiteException) {
                // handle the exception by the user
                exceptionHandler()
            }
        }

    }

    fun deleteAllFolders() {
        viewModelScope.launch (Dispatchers.IO) {
            repository?.deleteAllFolders()
        }
    }

    fun delete(folder: Folder) {
        viewModelScope.launch (Dispatchers.IO) {
            repository?.delete(folder)
        }
    }

    fun updateFolderImageUrl(folderName: String, newImageUrl: String) {
        viewModelScope.launch (Dispatchers.IO) {
            repository?.updateFolderImageUrl(folderName, newImageUrl)
        }
    }


}
