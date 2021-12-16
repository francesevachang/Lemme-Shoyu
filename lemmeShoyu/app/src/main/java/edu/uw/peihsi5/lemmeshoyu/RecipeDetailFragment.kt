package edu.uw.peihsi5.lemmeshoyu

import android.annotation.SuppressLint
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
import edu.uw.peihsi5.lemmeshoyu.network.Recipe
import edu.uw.peihsi5.lemmeshoyu.network.RecipeApi
import edu.uw.peihsi5.lemmeshoyu.network.Step
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.app.Activity
import android.widget.ArrayAdapter
import edu.uw.peihsi5.lemmeshoyu.network.StepsResponse


class RecipeDetailFragment : Fragment() {


    private var recipe: Recipe? = null
    private val API_KEY = "a819550bd27e4c86943cebc170e07408"
    private lateinit var adapter: StepsAdapter
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

        RecipeApi.retrofitService.getStep(recipe!!.id, API_KEY)
            .enqueue(object : Callback<List<StepsResponse>> {
                override fun onResponse(
                    call: Call<List<StepsResponse>>,
                    response: Response<List<StepsResponse>>
                ) {
                    val body = response.body()
                    Log.v(TAG, "$body")
                    if (body != null) {
                        for (i in body.indices) {
                            adapter = StepsAdapter(response.body()?.get(i)?.steps)
                        }
                    }
                    val recycler = rootView.findViewById<RecyclerView>(R.id.step_recycler_list)
                    recycler.layoutManager = LinearLayoutManager(activity)
                    recycler.adapter = adapter

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
                holder.stepView.text = (position + 1).toString() + ". " + theItem.step
                Log.d(TAG, theItem?.step.toString())
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



}