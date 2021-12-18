/**
 * Pacy Wu: I wrote the data class Recipe, which stores the recipe information, including
 * recipe name, recipe image url, and the folder it belongs to.
 **/

package edu.uw.peihsi5.lemmeshoyu.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * The recipe class that contains information about recipe id recipe name, recipe image url,
 * and the folder that contains recipe
 **/
@Entity (tableName = "recipesTable")
data class Recipe (
    @PrimaryKey val idOnSpoonacular: Int,
    @ColumnInfo(name = "recipeName") val recipeName: String,
    @ColumnInfo(name = "recipeImageUrl") val recipeImageUrl: String,
    @ColumnInfo(name = "recipeFolder") val recipeFolder: String,
)
