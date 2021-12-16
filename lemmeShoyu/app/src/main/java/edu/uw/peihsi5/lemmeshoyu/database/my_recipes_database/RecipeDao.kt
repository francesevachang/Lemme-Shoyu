/**
 * Pacy Wu: I wrote the RecipeDao to query, insert data, and delete data in the local database.
 **/

package edu.uw.peihsi5.lemmeshoyu.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.*
import androidx.room.Query

@Dao
interface RecipeDao {
    @Insert(onConflict = IGNORE)
    suspend fun insert(recipe: Recipe)

    @Query("DELETE FROM recipesTable")
    suspend fun deleteAllRecipes()

    @Delete
    suspend fun delete(recipe: Recipe)

    @Query("SELECT * FROM recipesTable WHERE recipeFolder = :folderName")
    fun getAllRecipesInFolder(folderName: String): LiveData<List<Recipe>>

    @Query("SELECT * FROM recipesTable")
    fun getAllRecipes(): LiveData<List<Recipe>>
}