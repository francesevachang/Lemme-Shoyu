package edu.uw.peihsi5.lemmeshoyu.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "myRecipesTable")
data class MyRecipes (
    @PrimaryKey private val idOnSpoonacular: Int,
    @ColumnInfo(name = "recipeName") private val recipeName: String,
    @ColumnInfo(name = "recipeImageUrl") private val recipeImageUrl: String,
    @ColumnInfo(name = "recipeFolder") private val recipeFolder: String,
)
