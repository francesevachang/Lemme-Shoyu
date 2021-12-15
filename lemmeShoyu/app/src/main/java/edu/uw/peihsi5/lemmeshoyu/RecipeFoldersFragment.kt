package edu.uw.peihsi5.lemmeshoyu

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
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

private const val TAG = ".RecipeFoldersFragment"

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class RecipeFoldersFragment : Fragment(), ViewOnClickListenerInterface<Folder>,
    BindDataToViewHolderInterface<Folder>, DeleteFromDatabaseInterface<Folder> {

    private lateinit var folderRecipeListsAdapter: FolderRecipeListsAdapter<Folder>
    private lateinit var viewModel: FolderViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val orientation = resources.configuration.orientation

        val rootView = inflater.inflate(R.layout.fragment_recipe_folders, container, false)

        folderRecipeListsAdapter = FolderRecipeListsAdapter(
            requireContext(),
            this,
            this,
            this)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application))
            .get(FolderViewModel::class.java)

        val folderObserver = Observer<List<Folder>> {
            folderRecipeListsAdapter.submitList(it)
        }
        viewModel.allFolders?.observe(viewLifecycleOwner, folderObserver)

//            viewModel.insertFolder(
//                Folder(
//                    "testFolder2",
//                    "https://spoonacular.com/recipeImages/716429-556x370.jpg"
//                )
//            ) { this.insertExceptionHandler() }

        val recycler: RecyclerView = rootView.findViewById(R.id.folders_recyclerview)
        recycler.adapter = folderRecipeListsAdapter

        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape
            recycler.layoutManager = GridLayoutManager(activity, 4)
        } else {
            // In portrait
            recycler.layoutManager = GridLayoutManager(activity, 2)
        }

        val addFolderButton = rootView.findViewById<FloatingActionButton>(R.id.floating_add_folder_button)
        addFolderButton.setOnClickListener {
            val dialog = AddFolderDialogFragment()
            dialog.show(requireActivity().supportFragmentManager, TAG)
        }

        return rootView
    }

    override fun viewOnClickListener(item: Folder) {
        val action = RecipeFoldersFragmentDirections.actionToRecipeListInFolderFragment(item.folderName)
        findNavController().navigate(action)
    }

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

    override fun deleteFromDatabase(item: Folder) {
        viewModel.delete(item)

        // TODO delete from recipe database as well
    }


}