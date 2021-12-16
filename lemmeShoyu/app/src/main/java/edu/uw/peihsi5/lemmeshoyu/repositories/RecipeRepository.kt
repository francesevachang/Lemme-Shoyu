/**
 * Pacy Wu: I wrote the RecipeRepository as an entrance of inserting, deleting, and retrieving data
 * in the recipe database.
 **/

package edu.uw.peihsi5.lemmeshoyu.repositories

import androidx.lifecycle.LiveData
import edu.uw.peihsi5.lemmeshoyu.database.Recipe
import edu.uw.peihsi5.lemmeshoyu.database.RecipeDao

class RecipeRepository(private val recipeDao: RecipeDao) {

    /** Insert the given recipe to the database **/
    suspend fun insert(recipe: Recipe) {
        recipeDao.insert(recipe)
    }

    /** Delete the given recipe in the database **/
    suspend fun delete(recipe: Recipe) {
        recipeDao.delete(recipe)
    }

    /** Delete all recipes in the database **/
    suspend fun deleteAllRecipes() {
        recipeDao.deleteAllRecipes()
    }

    /** Get all recipes in the given folder in the database and return it as a LiveData object **/
    fun getAllRecipesInFolder(folderName: String): LiveData<List<Recipe>>{
        return recipeDao.getAllRecipesInFolder(folderName)
    }

    suspend fun deleteRecipesWithFolderName(folderName: String) {
        recipeDao.deleteRecipesWithFolderName(folderName)
    }

}