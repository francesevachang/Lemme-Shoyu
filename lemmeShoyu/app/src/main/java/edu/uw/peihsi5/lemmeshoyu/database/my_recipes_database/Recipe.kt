package edu.uw.peihsi5.lemmeshoyu.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "recipesTable")
data class Recipe (
    @PrimaryKey val idOnSpoonacular: Int,
    @ColumnInfo(name = "recipeName") val recipeName: String,
    @ColumnInfo(name = "recipeImageUrl") val recipeImageUrl: String,
    @ColumnInfo(name = "recipeFolder") val recipeFolder: String,
)
