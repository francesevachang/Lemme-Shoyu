package edu.uw.peihsi5.lemmeshoyu.repositories

import androidx.lifecycle.LiveData
import edu.uw.peihsi5.lemmeshoyu.database.Recipe
import edu.uw.peihsi5.lemmeshoyu.database.RecipeDao

class RecipeRepository(private val recipeDao: RecipeDao) {

    suspend fun insert(recipe: Recipe) {
        recipeDao.insert(recipe)
    }

    suspend fun delete(recipe: Recipe) {
        recipeDao.delete(recipe)
    }

    suspend fun deleteAllRecipes() {
        recipeDao.deleteAllRecipes()
    }

    fun getAllRecipesInFolder(folderName: String): LiveData<List<Recipe>>{
        return recipeDao.getAllRecipesInFolder(folderName)

    }

}