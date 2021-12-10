package edu.uw.peihsi5.lemmeshoyu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.*
import com.bumptech.glide.Glide
import edu.uw.peihsi5.lemmeshoyu.database.Folder
import edu.uw.peihsi5.lemmeshoyu.viewmodels.FolderViewModel

private const val TAG = ".RecipeFoldersFragment"

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class RecipeFoldersFragment : Fragment(), ViewOnClickListenerInterface<Folder>,
    BindDataToViewHolderInterface<Folder> {

    private lateinit var folderRecipeListsAdapter: FolderRecipeListsAdapter<Folder>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val rootView = inflater.inflate(R.layout.fragment_recipe_folders, container, false)

        folderRecipeListsAdapter = FolderRecipeListsAdapter(requireContext(), this, this)

        val viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application))
            .get(FolderViewModel::class.java)

        val folderObserver = Observer<List<Folder>> {
            folderRecipeListsAdapter.submitList(it)
        }
        viewModel.allFolders?.observe(viewLifecycleOwner, folderObserver)

        // TODO NOTE:
        // viewModel.insertFolder(Folder("testFolder2", "https://spoonacular.com/recipeImages/716429-556x370.jpg"))

        val recycler: RecyclerView = rootView.findViewById(R.id.folders_recyclerview)
        recycler.adapter = folderRecipeListsAdapter
        recycler.layoutManager = GridLayoutManager(activity, 2)

        return rootView

    }

    override fun viewOnClickListener(folder: Folder) {
        val action = RecipeFoldersFragmentDirections.actionToRecipeListInFolderFragment(folder.folderName)
        findNavController().navigate(action)
    }

    override fun bindDataToViewHolder(folder: Folder, holder: FolderRecipeListsAdapter<Folder>.ViewHolder) {
        // load folder image
        Glide.with(this)
            .load(folder.folderImageUrl)
            .error(R.drawable.error_image)
            .into(holder.itemImage)

        // load folder name
        holder.itemTextView.text = folder.folderName
    }
}