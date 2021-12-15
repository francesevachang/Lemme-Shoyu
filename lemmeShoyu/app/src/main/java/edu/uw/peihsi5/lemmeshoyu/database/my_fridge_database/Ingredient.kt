package edu.uw.peihsi5.lemmeshoyu.database.my_fridge_database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "myFridgeTable")
data class Ingredient (
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // no duplicates
    @ColumnInfo(name = "itemName") val itemName: String,
    @ColumnInfo(name = "expireYear") val expireYear: Int,
    @ColumnInfo(name = "expireMonth") val expireMonth: Int,
    @ColumnInfo(name = "expireDay") val expireDay: Int
)

