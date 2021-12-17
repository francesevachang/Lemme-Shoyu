/**
 * Pacy Wu: I wrote the RecipeViewModel class to keep track of the data
 * in LiveData in recipe database.
 **/

package edu.uw.peihsi5.lemmeshoyu.viewmodels

import android.app.Application
import androidx.lifecycle.*
import edu.uw.peihsi5.lemmeshoyu.database.Recipe
import edu.uw.peihsi5.lemmeshoyu.database.my_recipes_database.RecipeDatabase
import edu.uw.peihsi5.lemmeshoyu.repositories.RecipeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = ".RecipeViewModel"

/** THe RecipeViewModel class that keeps track of the data in recipe database **/
class RecipeViewModel(application: Application, val folderName: String): AndroidViewModel(application) {
    var allRecipes: LiveData<List<Recipe>>? = null
    var repository: RecipeRepository? = null

    init {
        val recipeDao = RecipeDatabase.getDatabase(application)?.getRecipeDao()
        if (recipeDao != null) {
            repository = RecipeRepository(recipeDao)
            allRecipes = repository!!.getAllRecipesInFolder(folderName)
        }
    }

    /** Insert the given recipe to the recipe database **/
    fun insertRecipe(recipe: Recipe) {
        viewModelScope.launch (Dispatchers.IO) {
            repository?.insert(recipe)
        }
    }

    /** Delete all recipes in the recipe database **/
    fun deleteAllRecipes() {
        viewModelScope.launch (Dispatchers.IO) {
            repository?.deleteAllRecipes()
        }
    }

    /** Delete the given recipe in the recipe database **/
    fun deleteRecipe(recipe: Recipe) {
        viewModelScope.launch (Dispatchers.IO) {
            repository?.delete(recipe)
        }
    }

    /** Delete the recipes in the given folder **/
    fun deleteRecipesWithFolderName(folderName: String) {
        viewModelScope.launch (Dispatchers.IO) {
            repository?.deleteRecipesWithFolderName(folderName)
        }
    }


}