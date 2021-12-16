/**
 * Frances Chang: I wrote the MyFridgeRepository as an entrance of inserting, deleting, and retrieving data
 * in the my fridge database, which contains all the ingredients the user has.
 **/


package edu.uw.peihsi5.lemmeshoyu.repositories

import androidx.lifecycle.LiveData
import edu.uw.peihsi5.lemmeshoyu.database.my_fridge_database.Ingredient
import edu.uw.peihsi5.lemmeshoyu.database.my_fridge_database.MyFridgeDao

class MyFridgeRepository(private val myFridgeDao: MyFridgeDao) {

    /** Get all the ingredients in the database and return it as a LiveData object **/
    val allIngredients: LiveData<List<Ingredient>> = myFridgeDao.getAllIngredients()

    /** Insert the given ingredient to the database **/
    suspend fun insert(ingredient: Ingredient) {
        myFridgeDao.insert(ingredient)
    }

    /** Delete the given ingredient in the database **/
    suspend fun delete(ingredient: Ingredient) {
        myFridgeDao.delete(ingredient)
    }
}