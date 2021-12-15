package edu.uw.peihsi5.lemmeshoyu.database.my_fridge_database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "myFridgeTable")
data class Ingredient (
    @PrimaryKey val itemName: String, // no duplicates
    @ColumnInfo(name = "expireYear") val expireYear: Int,
    @ColumnInfo(name = "expireMonth") val expireMonth: Int,
    @ColumnInfo(name = "expireDay") val expireDay: Int
)

