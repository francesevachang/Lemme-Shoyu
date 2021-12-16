/**
 * Pacy Wu: I wrote the class RecipeFoldersFragment, which shows all the folders
 * in the home page. If it is the first-time user, the home page will autogenerate
 * an example folder. Each folder will navigate to the page containing all recipes
 * saved in the selected folder. The users are able to add folder, delete folder,
 * and delete recipes.
 **/

package edu.uw.peihsi5.lemmeshoyu

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.*
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import edu.uw.peihsi5.lemmeshoyu.adapters.BindDataToViewHolderInterface
import edu.uw.peihsi5.lemmeshoyu.adapters.DeleteFromDatabaseInterface
import edu.uw.peihsi5.lemmeshoyu.adapters.FolderRecipeListsAdapter
import edu.uw.peihsi5.lemmeshoyu.adapters.ViewOnClickListenerInterface
import edu.uw.peihsi5.lemmeshoyu.database.Folder
import edu.uw.peihsi5.lemmeshoyu.dialogs.AddFolderDialogFragment
import edu.uw.peihsi5.lemmeshoyu.viewmodels.FolderViewModel
import edu.uw.peihsi5.lemmeshoyu.viewmodels.RecipeViewModel


private const val TAG = ".RecipeFoldersFragment"

class RecipeFoldersFragment : Fragment(), ViewOnClickListenerInterface<Folder>,
    BindDataToViewHolderInterface<Folder>, DeleteFromDatabaseInterface<Folder> {

    private lateinit var folderRecipeListsAdapter: FolderRecipeListsAdapter<Folder>
    private lateinit var viewModel: FolderViewModel
    private lateinit var rootView: View

    /** Inflate the fragment view and add the required listener to the element. **/
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val orientation = resources.configuration.orientation

        rootView = inflater.inflate(R.layout.fragment_recipe_folders, container, false)

        folderRecipeListsAdapter = FolderRecipeListsAdapter(
            requireContext(),
            this,
            this,
            this
        )

        // view model getting all folders
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )
            .get(FolderViewModel::class.java)

        val folderObserver = Observer<List<Folder>> {
            folderRecipeListsAdapter.submitList(it)
        }
        viewModel.allFolders?.observe(viewLifecycleOwner, folderObserver)


        // recycler view of the folder list
        val recycler: RecyclerView = rootView.findViewById(R.id.folders_recyclerview)
        recycler.adapter = folderRecipeListsAdapter

        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape
            recycler.layoutManager = GridLayoutManager(activity, 4)
        } else {
            // In portrait
            recycler.layoutManager = GridLayoutManager(activity, 2)
        }

        // shared preference - first time user
        val sharedPref = requireActivity().getSharedPreferences(
            getString(R.string.first_time_used_key), Context.MODE_PRIVATE
        )
        val defaultFirstTime = resources.getString(R.string.default_first_time_boolean)
        val firstTime = sharedPref.getString(getString(R.string.first_time), defaultFirstTime)

        // if first time used, generate an example folder
        if (firstTime.equals(defaultFirstTime)) {
            viewModel.insertFolder(
                Folder(
                    "Default Folder",
                    "https://spoonacular.com/recipeImages/716429-556x370.jpg"
                )
            ) { Log.v(TAG, "fail to insert default folder") }
            with(sharedPref.edit()) {
                putString(getString(R.string.first_time), "False")
                apply()
            }
        }

        // add the onClickListener to add folder button
        val addFolderButton =
            rootView.findViewById<FloatingActionButton>(R.id.floating_add_folder_button)
        addFolderButton.setOnClickListener {
            val dialog = AddFolderDialogFragment()
            dialog.show(requireActivity().supportFragmentManager, TAG)
        }

        return rootView
    }

    /** The onclickListener of the holder in recyclerview, which will navigate to the
     * fragment containing all recipes in the selected folder. **/
    override fun viewOnClickListener(item: Folder) {
        val action = RecipeFoldersFragmentDirections.actionToRecipeListInFolderFragment(item.folderName)
        findNavController().navigate(action)
    }

    /** Bind the given data (folder) to the given view holder in recycler view. **/
    override fun bindDataToViewHolder(item: Folder, holder: FolderRecipeListsAdapter<Folder>.ViewHolder) {
        // load folder image
        if (item.folderImageUrl != null) {
            Glide.with(this)
                .load(item.folderImageUrl)
                .error(R.drawable.error_image)
                .into(holder.itemImage)
        }

        // load folder name
        holder.itemTextView.text = item.folderName

        holder.item = item
    }

    /** Delete the given item, which is a folder, in the database. **/
    override fun deleteFromDatabase(item: Folder) {
        viewModel.delete(item)

        // delete recipes in the folder as well
        val recipeViewModel: RecipeViewModel by viewModels {
            RecipeViewModelFactory(requireActivity().application, item.folderName) }
        recipeViewModel.deleteRecipesWithFolderName(item.folderName)
    }

    inner class RecipeViewModelFactory (application: Application, param: String) :
        ViewModelProvider.Factory {
        private val mApplication: Application = application
        private val mParam: String = param
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return RecipeViewModel(mApplication, mParam) as T
        }
    }

}