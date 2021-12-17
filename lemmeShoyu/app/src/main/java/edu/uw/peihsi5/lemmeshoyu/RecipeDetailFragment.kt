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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import edu.uw.peihsi5.lemmeshoyu.dialogs.ChooseFolderDialogFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import edu.uw.peihsi5.lemmeshoyu.network.*


class RecipeDetailFragment : Fragment() {


    private var recipe: Recipe? = null
    private val API_KEY = "594baa11072841208ee7ad173fedd4dc"
    private val TAG = "RecipeDetailFragment"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args: RecipeDetailFragmentArgs by navArgs()
        recipe = args.recipe


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false)
        rootView.findViewById<TextView>(R.id.recipe_title).text = recipe!!.title
        val recipePhoto: ImageView = rootView.findViewById<ImageView>(R.id.recipe_photo)
        Glide.with(rootView).load(recipe!!.imagePath).into(recipePhoto)

        rootView.findViewById<FloatingActionButton>(R.id.add_recipe_to_folder_button).setOnClickListener{
            val dialog = ChooseFolderDialogFragment(recipe!!)
            dialog.show(requireActivity().supportFragmentManager, "Choose Folder Dialog")
        }


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

        RecipeApi.retrofitService.getStep(recipe!!.id, API_KEY)
            .enqueue(object : Callback<List<StepsResponse>> {
                override fun onResponse(
                    call: Call<List<StepsResponse>>,
                    response: Response<List<StepsResponse>>
                ) {
                    val body = response.body()
                    Log.v(TAG, "$body")
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

                }

                override fun onFailure(call: Call<List<StepsResponse>>, t: Throwable) {
                    Log.e(TAG, "Failure: ${t.message}")

                }


            })



        return rootView
    }






}