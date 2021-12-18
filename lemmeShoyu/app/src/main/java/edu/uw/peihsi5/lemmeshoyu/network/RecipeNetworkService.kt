/**
 * Christine Tang: I wrote the RecipeApiService interface to connect the app with API,
 * initialize moshi and retrofit, and multiple data class to represent the data from API
 **/
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
    //for search recipe
    @GET("complexSearch")
    fun searchRecipe(@Query("query")query:String, @Query("apiKey")apiKey: String):Call<RecipeSearchResponse>

    //for getting the ingredients of the recipe
    @GET("{id}/ingredientWidget.json")
    fun getIngredient(@Path("id")recipeID:Int, @Query("apiKey")apiKey: String):Call<Ingredients>

    //for getting the steps of the recipe
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


//data class that contains individual recipe
@Parcelize
data class Recipe(
    @Json(name = "id")
    val id: Int,

    @Json(name = "title")
    var title: String,

    @Json(name = "image")
    val imagePath: String
) : Parcelable

//data class that contains the search response which is a list of recipe
data class RecipeSearchResponse(
    val results: List<Recipe>
)

//data class that contains individual steps of the recipe
@Parcelize
data class Step(
    @Json(name = "number")
    val number: Int?,

    @Json(name = "step")
    val step: String?
): Parcelable

//data class that contains a list of steps
data class StepsResponse(
    @Json(name = "steps")
    val steps: List<Step>?
)

//data class that contains each ingredient's value and unit
@Parcelize
data class Ingredient(
    @Json(name = "value")
    val value: Double?,

    @Json(name = "unit")
    val unit: String?
): Parcelable

//data class that contains a list of ingredient's value and unit
@Parcelize
data class ValueUnit(
    @Json(name = "us")
    val amountUS: Ingredient?
): Parcelable

//data class that contains the name of the ingredient and a list of ingredient's value and unit
@Parcelize
data class Amount(
    @Json(name = "amount")
    val amount: ValueUnit?,

    @Json(name = "name")
    val name: String?
): Parcelable

//data class that contains a list of the recipe's ingredients which includes
// the ingredient's name, value, and unit
data class Ingredients(
    @Json(name = "ingredients")
    val ingredients: List<Amount>?
)


