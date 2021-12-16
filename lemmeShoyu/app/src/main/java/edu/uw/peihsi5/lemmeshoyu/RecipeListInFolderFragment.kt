/**
 * Pacy Wu: I wrote the class RecipeListInFolderFragment, which shows all the recipes
 * in the selected folder. Each recipe navigates to a detailed page containing all the
 * instructions and required ingredients.
 **/

package edu.uw.peihsi5.lemmeshoyu

import android.app.Application
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import edu.uw.peihsi5.lemmeshoyu.database.Recipe
import edu.uw.peihsi5.lemmeshoyu.databinding.FragmentRecipeListInFolderBinding
import edu.uw.peihsi5.lemmeshoyu.viewmodels.RecipeViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import edu.uw.peihsi5.lemmeshoyu.adapters.BindDataToViewHolderInterface
import edu.uw.peihsi5.lemmeshoyu.adapters.DeleteFromDatabaseInterface
import edu.uw.peihsi5.lemmeshoyu.adapters.FolderRecipeListsAdapter
import edu.uw.peihsi5.lemmeshoyu.adapters.ViewOnClickListenerInterface
import edu.uw.peihsi5.lemmeshoyu.network.Recipe as RecipeInNetwork

private const val TAG = ".RecipeListInFolderFragment"

class RecipeListInFolderFragment : Fragment(), ViewOnClickListenerInterface<Recipe>,
    BindDataToViewHolderInterface<Recipe>, DeleteFromDatabaseInterface<Recipe> {

    private var _binding:FragmentRecipeListInFolderBinding? = null
    private val binding get() = _binding!!
    private lateinit var folderName: String
    private lateinit var recipesRecipeListsAdapter: FolderRecipeListsAdapter<Recipe>
    private lateinit var viewModel: RecipeViewModel

    /** Initialized the passed in parameter. **/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args: RecipeListInFolderFragmentArgs by navArgs()
        folderName = args.folderName

    }

    /** Inflate the fragment view and add the required listener to the element. **/
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRecipeListInFolderBinding.inflate(inflater, container, false)
        val orientation = resources.configuration.orientation

        val rootView = binding.root
        recipesRecipeListsAdapter = FolderRecipeListsAdapter(
            requireContext(),
            this,
            this,
            this)

        val viewModel: RecipeViewModel by viewModels { RecipeViewModelFactory(requireActivity().application, folderName) }
        this.viewModel = viewModel

        val recipesObserver = Observer<List<Recipe>> {
            recipesRecipeListsAdapter.submitList(it)
        }
        viewModel.allRecipes?.observe(viewLifecycleOwner, recipesObserver)

        val recycler: RecyclerView = rootView.findViewById(R.id.folders_recyclerview)
        recycler.adapter = recipesRecipeListsAdapter
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape
            recycler.layoutManager = GridLayoutManager(activity, 4)
        } else {
            // In portrait
            recycler.layoutManager = GridLayoutManager(activity, 2)
        }
        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /** The onclickListener of the holder in recyclerview, which will navigate to recipe detail page. **/
    override fun viewOnClickListener(item: Recipe) {

        val action = RecipeListInFolderFragmentDirections.actionToRecipeDetail(
            RecipeInNetwork(item.idOnSpoonacular, item.recipeName, item.recipeImageUrl))
        findNavController().navigate(action)

        Log.v(TAG, "navigate to recipe detail fragment")
    }

    /** Bind the given data (recipe) to the given view holder in recycler view. **/
    override fun bindDataToViewHolder(item: Recipe, holder: FolderRecipeListsAdapter<Recipe>.ViewHolder) {
        // load recipe image
        Glide.with(this)
            .load(item.recipeImageUrl)
            .error(R.drawable.error_image)
            .into(holder.itemImage)

        // load recipe name
        holder.itemTextView.text = item.recipeName

        holder.item = item

    }

    inner class RecipeViewModelFactory (application: Application, param: String) :
        ViewModelProvider.Factory {
        private val mApplication: Application = application
        private val mParam: String = param
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return RecipeViewModel(mApplication, mParam) as T
        }

    }

    /** Delete the given item, which is a recipe, in the database. **/
    override fun deleteFromDatabase(item: Recipe) {
        viewModel.deleteRecipe(item)
    }
}

