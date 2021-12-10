package edu.uw.peihsi5.lemmeshoyu.database.my_recipes_database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import edu.uw.peihsi5.lemmeshoyu.database.Recipe
import edu.uw.peihsi5.lemmeshoyu.database.RecipeDao

@Database(entities = arrayOf(Recipe::class), version = 1, exportSchema = false)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun getRecipeDao(): RecipeDao

    companion object {
        @Volatile
        private var INSTANCE: RecipeDatabase? = null

        fun getDatabase(context: Context): RecipeDatabase? {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RecipeDatabase::class.java, "recipesTable"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }


    }
}