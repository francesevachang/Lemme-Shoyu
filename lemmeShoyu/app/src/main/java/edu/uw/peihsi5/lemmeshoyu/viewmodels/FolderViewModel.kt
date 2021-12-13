package edu.uw.peihsi5.lemmeshoyu.viewmodels

import android.app.Application
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
        var folderDao = FolderDatabase.getDatabase(application)?.getFoldersDao()
        if (folderDao != null) {
            repository = FolderRepository(folderDao)
            allFolders = repository!!.allFolders
        }
    }

    fun insertFolder(folder: Folder) {
        viewModelScope.launch (Dispatchers.IO) {
            repository?.insert(folder)
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
}