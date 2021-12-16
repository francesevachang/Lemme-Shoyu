package edu.uw.peihsi5.lemmeshoyu.network


import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.parcelize.Parcelize
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*
import retrofit2.http.Body

import retrofit2.http.POST
import java.util.function.BinaryOperator


private const val BASE_URL = "https://api.spoonacular.com/recipes/"


//The API Interface
interface RecipeApiService{

    @GET("complexSearch")
    fun searchRecipe(@Query("query")query:String, @Query("apiKey")apiKey: String):Call<RecipeSearchResponse>

    @GET("{id}/ingredientWidget.json")
    fun getIngredient(@Path("id")recipeID:Int, @Query("apiKey")apiKey: String):Call<Ingredients>

    @GET("{id}/analyzedInstructions")
    fun getStep(@Path("id")recipeID:Int, @Query("apiKey")apiKey: String):Call<List<StepsResponse>>
}
//initialize Moshi
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

//initialize retrofit
private val client = OkHttpClient.Builder().build()
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .client(client)
    .build()

object RecipeApi {
    val retrofitService: RecipeApiService by lazy{
        retrofit.create(RecipeApiService::class.java)
    }
}

@Parcelize
data class Recipe(
    @Json(name = "id")
    val id: Int,

    @Json(name = "title")
    var title: String,

    @Json(name = "image")
    val imagePath: String
) : Parcelable

data class RecipeSearchResponse(
    val results: List<Recipe>
)

@Parcelize
data class Step(
    @Json(name = "number")
    val number: Int?,

    @Json(name = "step")
    val step: String?
): Parcelable

data class StepsResponse(
    @Json(name = "steps")
    val steps: List<Step>?
)


@Parcelize
data class Ingredient(
    @Json(name = "value")
    val value: Double?,

    @Json(name = "unit")
    val unit: Double?
): Parcelable

@Parcelize
data class ValueUnit(
    @Json(name = "us")
    val amountUS: List<Ingredient>?
): Parcelable


data class Amount(
    @Json(name = "amount")
    val amount: List<ValueUnit>?,

    @Json(name = "name")
    val name: String?
)

data class Ingredients(
    @Json(name = "ingredients")
    val ingredients: List<Amount>?
)


