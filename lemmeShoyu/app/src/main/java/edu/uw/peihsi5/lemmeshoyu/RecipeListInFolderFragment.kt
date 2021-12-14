package edu.uw.peihsi5.lemmeshoyu

import android.app.Application
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
import edu.uw.peihsi5.lemmeshoyu.adapters.BindDataToViewHolderInterface
import edu.uw.peihsi5.lemmeshoyu.adapters.DeleteFromDatabaseInterface
import edu.uw.peihsi5.lemmeshoyu.adapters.FolderRecipeListsAdapter
import edu.uw.peihsi5.lemmeshoyu.adapters.ViewOnClickListenerInterface

private const val TAG = ".RecipeListInFolderFragment"

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class RecipeListInFolderFragment : Fragment(), ViewOnClickListenerInterface<Recipe>,
    BindDataToViewHolderInterface<Recipe>, DeleteFromDatabaseInterface<Recipe> {

    private var _binding:FragmentRecipeListInFolderBinding? = null
    private val binding get() = _binding!!
    private lateinit var folderName: String
    private lateinit var recipesRecipeListsAdapter: FolderRecipeListsAdapter<Recipe>
    private lateinit var viewModel: RecipeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args: RecipeListInFolderFragmentArgs by navArgs()
        folderName = args.folderName

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRecipeListInFolderBinding.inflate(inflater, container, false)
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

        // TODO Note: insertRecipe
//        viewModel.insertRecipe(Recipe(715538, "Pasta with Garlic, Scallions, Cauliflower & Breadcrumbs",
//            "https://spoonacular.com/recipeImages/715538-312x231.jpg", "testFolder2"))

        val recycler: RecyclerView = rootView.findViewById(R.id.folders_recyclerview)
        recycler.adapter = recipesRecipeListsAdapter
        recycler.layoutManager = GridLayoutManager(activity, 2)

        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun viewOnClickListener(folder: Recipe) {
        Log.v(TAG, "navigate to recipe detail fragment")
    }

    override fun bindDataToViewHolder(recipe: Recipe, holder: FolderRecipeListsAdapter<Recipe>.ViewHolder) {
        // load recipe image
        Glide.with(this)
            .load(recipe.recipeImageUrl)
            .error(R.drawable.error_image)
            .into(holder.itemImage)

        // load recipe name
        holder.itemTextView.text = recipe.recipeName

        holder.item = recipe

    }

    inner class RecipeViewModelFactory(application: Application, param: String) :
        ViewModelProvider.Factory {
        private val mApplication: Application = application
        private val mParam: String = param
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return RecipeViewModel(mApplication, mParam) as T
        }
    }

    override fun deleteFromDatabase(item: Recipe) {
        viewModel.deleteRecipe(item)
    }
}

