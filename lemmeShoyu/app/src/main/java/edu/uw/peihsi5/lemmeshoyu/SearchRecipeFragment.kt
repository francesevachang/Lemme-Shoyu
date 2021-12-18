/**
 * Christine Tang: I wrote the class SearchRecipeFragment which allows users to
 * search recipes by any keywords and will show a list of results that contains
 * the recipe's title and photo.
 **/
package edu.uw.peihsi5.lemmeshoyu

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import edu.uw.peihsi5.lemmeshoyu.network.Recipe
import edu.uw.peihsi5.lemmeshoyu.viewmodels.SearchRecipeViewModel

class SearchRecipeFragment : Fragment() {
    private val TAG = "SearchRecipeFragment"
    private lateinit var searchAdapter: SearchListAdapter
    private lateinit var viewModel: SearchRecipeViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    //Inflate the fragment view, get the data from the viewmodel to the recycler view,
    // and add the listener to the search button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        searchAdapter = SearchListAdapter()

        viewModel = ViewModelProvider(this).get(SearchRecipeViewModel::class.java)
        val recipeObserver = Observer<List<Recipe>>{
            Log.v(TAG, "Updating: $it")
            searchAdapter.submitList(it)
        }


        viewModel.recipeData.observe(viewLifecycleOwner, recipeObserver)


        val rootView =  inflater.inflate(R.layout.fragment_search_recipe, container, false)

        val recycler = rootView.findViewById<RecyclerView>(R.id.recycler_list)
        recycler.layoutManager = LinearLayoutManager(activity)
        recycler.adapter = searchAdapter



        rootView.findViewById<Button>(R.id.search_button).setOnClickListener{
            val input = rootView.findViewById<EditText>(R.id.edit_input).text.toString()
            Log.v(TAG, "Searching recipes for $input")
            viewModel.searchRecipes(input)

        }
        return rootView


    }


    // the adapter for search result's recycler view
    inner class SearchListAdapter(): ListAdapter<Recipe, SearchListAdapter.RecipeViewHolder>(RecipeSearchDiffCallback()){

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
            val inflatedView = LayoutInflater.from(parent.context).inflate(
                R.layout.list_item_recipe,
                parent,
                false
            )
            return RecipeViewHolder(inflatedView)
        }

        override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
            val theItem = getItem(position)
            return holder.bind(theItem)
        }
        inner class RecipeViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
            val recipeText: TextView = itemView.findViewById<TextView>(R.id.recipe_text)
            val recipePhoto: ImageView = itemView.findViewById<ImageView>(R.id.recipe_photo)

            fun bind(recipe: Recipe) {
                Glide.with(itemView.context).load(recipe.imagePath).into(recipePhoto)
                recipeText.text = recipe.title
                itemView.findViewById<CardView>(R.id.recipe_item).setOnClickListener{
                    Log.v(TAG, recipe.toString())
                    val action = SearchRecipeFragmentDirections.actionToRecipeDetailFragment(recipe)
                    findNavController().navigate(action)
                }
            }

        }


    }


    // callback function for the search adapter
    inner class RecipeSearchDiffCallback : DiffUtil.ItemCallback<Recipe>() {
        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem == newItem
        }
    }


}