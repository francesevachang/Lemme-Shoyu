package edu.uw.peihsi5.lemmeshoyu.database.my_fridge_database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Ingredient::class), version = 1, exportSchema = false)
abstract class MyFridgeDatabase : RoomDatabase() {
    abstract fun getMyFridgeDao(): MyFridgeDao

    companion object {
        @Volatile
        private var INSTANCE: MyFridgeDatabase? = null

        fun getDatabase(context: Context): MyFridgeDatabase? {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyFridgeDatabase::class.java, "myFridgeTable"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }


    }
}