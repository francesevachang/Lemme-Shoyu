package edu.uw.peihsi5.lemmeshoyu.database.my_fridge_database

import android.database.sqlite.SQLiteException
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.*
import androidx.room.Query

@Dao
interface MyFridgeDao {
    @Insert(onConflict = ABORT)
    @Throws(SQLiteException::class)
    suspend fun insert(ingredient: Ingredient)

    @Delete
    suspend fun delete(ingredient: Ingredient)

    @Query("SELECT * FROM myFridgeTable")
    fun getAllIngredients(): LiveData<List<Ingredient>>

}