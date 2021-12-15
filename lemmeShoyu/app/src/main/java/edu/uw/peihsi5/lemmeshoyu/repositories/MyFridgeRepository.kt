package edu.uw.peihsi5.lemmeshoyu.repositories

import androidx.lifecycle.LiveData
import edu.uw.peihsi5.lemmeshoyu.database.my_fridge_database.Ingredient
import edu.uw.peihsi5.lemmeshoyu.database.my_fridge_database.MyFridgeDao

class MyFridgeRepository(private val myFridgeDao: MyFridgeDao) {

    val allIngredients: LiveData<List<Ingredient>> = myFridgeDao.getAllIngredients()

    suspend fun insert(ingredient: Ingredient) {
        myFridgeDao.insert(ingredient)
    }

    suspend fun delete(ingredient: Ingredient) {
        myFridgeDao.delete(ingredient)
    }
}