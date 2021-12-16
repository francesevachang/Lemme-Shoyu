package edu.uw.peihsi5.lemmeshoyu.viewmodels

import android.app.Application
import androidx.lifecycle.*
import edu.uw.peihsi5.lemmeshoyu.database.Recipe
import edu.uw.peihsi5.lemmeshoyu.database.my_recipes_database.RecipeDatabase
import edu.uw.peihsi5.lemmeshoyu.repositories.RecipeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = ".RecipeViewModel"

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

    fun insertRecipe(recipe: Recipe) {
        viewModelScope.launch (Dispatchers.IO) {
            repository?.insert(recipe)
        }
    }

    fun deleteAllRecipes() {
        viewModelScope.launch (Dispatchers.IO) {
            repository?.deleteAllRecipes()
        }
    }

    fun deleteRecipe(recipe: Recipe) {
        viewModelScope.launch (Dispatchers.IO) {
            repository?.delete(recipe)
        }
    }

    fun deleteRecipesWithFolderName(folderName: String) {
        viewModelScope.launch (Dispatchers.IO) {
            repository?.deleteRecipesWithFolderName(folderName)
        }
    }


}