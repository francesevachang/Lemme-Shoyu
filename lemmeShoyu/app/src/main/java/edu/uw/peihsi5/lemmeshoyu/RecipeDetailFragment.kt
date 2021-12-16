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
    private lateinit var stepsAdapter: StepsAdapter
    private lateinit var ingredientsAdapter: IngredientAdapter
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

//        RecipeApi.retrofitService.getStep(recipe!!.id, API_KEY)
//            .enqueue(object : Callback<List<StepsResponse>> {
//                override fun onResponse(
//                    call: Call<List<StepsResponse>>,
//                    response: Response<List<StepsResponse>>
//                ) {
//                    val body = response.body()
//                    Log.v(TAG, "$body")
//                    stepsAdapter = StepsAdapter(response.body()?.get(0)?.steps)
//
//                    val recycler = rootView.findViewById<RecyclerView>(R.id.step_recycler_list)
//                    recycler.layoutManager = LinearLayoutManager(activity)
//                    recycler.adapter = stepsAdapter
//
//                }
//
//                override fun onFailure(call: Call<List<StepsResponse>>, t: Throwable) {
//                    Log.e(TAG, "Failure: ${t.message}")
//
//                }
//
//
//            })

//        RecipeApi.retrofitService.getIngredient(recipe!!.id, API_KEY)
//            .enqueue(object : Callback<Ingredients> {
//                override fun onResponse(
//                    call: Call<Ingredients>,
//                    response: Response<Ingredients>
//                ) {
//                    val body = response.body()
//                    Log.v(TAG, "$body")
//                    ingredientsAdapter = IngredientAdapter(response.body()?.ingredients)
//                    val recycler = rootView.findViewById<RecyclerView>(R.id.ingredient_recycler_list)
//                    recycler.layoutManager = LinearLayoutManager(activity)
//                    recycler.adapter = ingredientsAdapter
//
//                }
//
//                override fun onFailure(call: Call<Ingredients>, t: Throwable) {
//                    Log.e(TAG, "Failure: ${t.message}")
//
//                }
//
//
//            })
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
                        for (i in listAmount[0].indices) {
                            if (i == 0) {
                                textView.text = listAmount[0][i].name
                            } else {
                                textView.append("\n\n" + listAmount[0][i].name)
                            }
                        }
                    }

//                    if (listAmount != null) {
//                        for (i in listAmount.indices) {
//                            val listValueUnit = listAmount[i].amount
//                            if (listValueUnit != null) {
//                                for(j in listValueUnit.indices){
//                                    val listIngredient =  listValueUnit[j].amountUS
//                                    if (listIngredient != null) {
//                                        for(k in listIngredient.indices){
//                                            val unit = listIngredient[k].unit.toString()
//                                            val value = listIngredient[k].value.toString()
//                                            if (i == 0) {
//                                                textView.text = unit + " " + value + " " + listAmount[i].name
//                                            } else {
//                                                textView.append("\n\n" + unit + " " + value + " " + listAmount[i].name)
//                                            }
//
//                                        }
//                                    }
//
//                                }
//
//                            }
//                        }
//                    }



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

    inner class StepsAdapter(val steps: List<Step>?) :
        RecyclerView.Adapter<StepsAdapter.ViewHolder>() {
        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            //get a reference to the views that we want to modify per element in recyclerView
            val stepView: TextView = view.findViewById<TextView>(R.id.step_text)

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val inflatedView = LayoutInflater.from(parent.context).inflate(
                R.layout.list_item_step, //this xml
                parent,
                false
            )
            return ViewHolder(inflatedView)
        }

        //called whatever the data is connected to the View
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            if (steps != null) {
                val theItem = steps[position]
                holder.stepView.text = theItem.number.toString() + ". " + theItem.step
                Log.d(TAG, theItem.toString())
            }

        }

        override fun getItemCount(): Int {
            if (steps != null) {
                return steps.size
            } else {
                return 0
            }
        }


    }

    inner class IngredientAdapter(val ingredients: List<Amount>?) :
        RecyclerView.Adapter<IngredientAdapter.ViewHolder>() {
        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            //get a reference to the views that we want to modify per element in recyclerView
            val ingredientView: TextView = view.findViewById<TextView>(R.id.ingredient_text)

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val inflatedView = LayoutInflater.from(parent.context).inflate(
                R.layout.list_item_ingredient_name, //this xml
                parent,
                false
            )
            return ViewHolder(inflatedView)
        }


        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            if (ingredients != null) {
                val theItem = ingredients[position]
                holder.ingredientView.text = theItem.name
                Log.d(TAG, theItem.name.toString())
            }
        }

        override fun getItemCount(): Int {
            if (ingredients != null) {
                return ingredients.size
            } else {
                return 0
            }
        }


    }





}