/**
 * Frances Change and Pacy Wu: We wrote the MyFridgeViewModel class to keep track of the data
 * in LiveData in my fridge database.
 **/

package edu.uw.peihsi5.lemmeshoyu.viewmodels

import android.app.Application
import android.database.sqlite.SQLiteException
import android.util.Log
import androidx.lifecycle.*
import edu.uw.peihsi5.lemmeshoyu.database.my_fridge_database.Ingredient
import edu.uw.peihsi5.lemmeshoyu.database.my_fridge_database.MyFridgeDatabase
import edu.uw.peihsi5.lemmeshoyu.repositories.MyFridgeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = ".MyFridgeViewModel"

/** The MyFridgeViewModel class that keeps track of the data in my fridge database **/
class MyFridgeViewModel(application: Application): AndroidViewModel(application) {
    var allIngredients: LiveData<List<Ingredient>>? = null
    var repository: MyFridgeRepository? = null

    init {
        val myFridgeDao = MyFridgeDatabase.getDatabase(application)?.getMyFridgeDao()
        if (myFridgeDao != null) {
            repository = MyFridgeRepository(myFridgeDao)
            allIngredients = repository!!.allIngredients
        }
    }

    /** Insert the given ingredient to the database.
     * When inserting fails, exception handler in called **/
    fun insert(ingredient: Ingredient, exceptionHandler: () -> Unit) {
        viewModelScope.launch (Dispatchers.IO) {
            try {
                repository?.insert(ingredient)
            } catch(e: SQLiteException) {
                // handle the exception by the user
                exceptionHandler()
            }
        }

    }

    /** Delete the given ingredient in the database **/
    fun delete(ingredient: Ingredient) {
        viewModelScope.launch (Dispatchers.IO) {
            repository?.delete(ingredient)
        }
    }
}
