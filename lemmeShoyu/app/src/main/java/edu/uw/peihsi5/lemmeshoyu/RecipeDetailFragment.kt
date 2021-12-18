/**
 * Christine Tang: I wrote the class RecipeDetailFragment to show the recipe's information
 * which includes recipe's photo, title, ingredients, and steps to make the dish.
 **/
package edu.uw.peihsi5.lemmeshoyu

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import edu.uw.peihsi5.lemmeshoyu.dialogs.ChooseFolderDialogFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import edu.uw.peihsi5.lemmeshoyu.network.*


class RecipeDetailFragment : Fragment() {


    private var recipe: Recipe? = null
    private val API_KEY = "a819550bd27e4c86943cebc170e07408"
    private val TAG = "RecipeDetailFragment"

    //connect to the fragment's argument
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args: RecipeDetailFragmentArgs by navArgs()
        recipe = args.recipe


    }

    //Inflate the fragment view, and get the data of recipe's photo, title, ingredients,
    // and steps to make the dish.
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false)
        // get the data for recipe's title
        rootView.findViewById<TextView>(R.id.recipe_title).text = recipe!!.title
        val recipePhoto: ImageView = rootView.findViewById<ImageView>(R.id.recipe_photo)
        // get the data for recipe's photo
        Glide.with(rootView).load(recipe!!.imagePath).into(recipePhoto)

        rootView.findViewById<FloatingActionButton>(R.id.add_recipe_to_folder_button).setOnClickListener{
            val dialog = ChooseFolderDialogFragment.newInstance(recipe!!)
            dialog!!.show(requireActivity().supportFragmentManager, "Choose Folder Dialog")
        }

        // get the data for recipe's ingredients
        RecipeApi.retrofitService.getIngredient(recipe!!.id, API_KEY)
            .enqueue(object : Callback<Ingredients> {
                override fun onResponse(
                    call: Call<Ingredients>,
                    response: Response<Ingredients>
                ) {
                    val body = response.body()
                    Log.v(TAG, "$body")
                    val listAmount = response.body()?.ingredients
                    val textView = rootView.findViewById<TextView>(R.id.ingredient_list)
                    if (listAmount != null) {
                        for (i in listAmount.indices) {
                            val listValueUnit = listAmount[i].amount
                            val amountUS = listValueUnit?.amountUS
                            val unit = amountUS?.unit
                            val value = amountUS?.value
                            if (i == 0) {
                                textView.text = value.toString() + " " + unit + " " + listAmount[i].name
                            } else {
                                textView.append("\n\n" + value.toString() + " " + unit + " " + listAmount[i].name)
                            }
                        }
                    }




                }


                override fun onFailure(call: Call<Ingredients>, t: Throwable) {
                    Log.e(TAG, "Failure: ${t.message}")

                }


            })


        // get the data for recipe's steps
        RecipeApi.retrofitService.getStep(recipe!!.id, API_KEY)
            .enqueue(object : Callback<List<StepsResponse>> {
                override fun onResponse(
                    call: Call<List<StepsResponse>>,
                    response: Response<List<StepsResponse>>
                ) {
                    val body = response.body()
                    Log.v(TAG, "$body")
                    if (body!!.isNotEmpty()) {
                        val listStep = response.body()?.get(0)?.steps
                        var stepTextView = rootView.findViewById<TextView>(R.id.step_list)
                        if (listStep != null) {
                            for (i in listStep.indices) {
                                if (i == 0) {
                                    stepTextView.text = listStep[i].number.toString() + ". " + listStep[i].step
                                } else {
                                    stepTextView.append("\n\n" + listStep[i].number.toString() + ". " + listStep[i].step)
                                }
                            }
                        }
                    } else {
                        var stepTextView = rootView.findViewById<TextView>(R.id.step_list)
                        stepTextView.text = "There is no steps provided"
                    }


                }

                override fun onFailure(call: Call<List<StepsResponse>>, t: Throwable) {
                    Log.e(TAG, "Failure: ${t.message}")

                }


            })



        return rootView
    }






}