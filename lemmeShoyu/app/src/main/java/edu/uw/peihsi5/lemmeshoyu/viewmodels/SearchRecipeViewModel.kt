package edu.uw.peihsi5.lemmeshoyu.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.uw.peihsi5.lemmeshoyu.network.Recipe
import edu.uw.peihsi5.lemmeshoyu.network.RecipeApi
import edu.uw.peihsi5.lemmeshoyu.network.RecipeSearchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchRecipeViewModel : ViewModel() {
    private val TAG = "ViewModel"
    private val API_KEY = "594baa11072841208ee7ad173fedd4dc"
    private var _recipeData = MutableLiveData<List<Recipe>>()
    val recipeData: LiveData<List<Recipe>>
    get() = _recipeData



    fun searchRecipes(query:String){
        RecipeApi.retrofitService.searchRecipe(query, API_KEY).enqueue(object:
            Callback<RecipeSearchResponse> {
            override fun onResponse(call: Call<RecipeSearchResponse>, response: Response<RecipeSearchResponse>) {
                val body = response.body()
                Log.v(TAG, "$body")
                val recipes = body?.results
                _recipeData.value = recipes!!

            }

            override fun onFailure(call: Call<RecipeSearchResponse>, t: Throwable) {
                Log.e(TAG, "Failure: ${t.message}")

            }
        })
    }
}