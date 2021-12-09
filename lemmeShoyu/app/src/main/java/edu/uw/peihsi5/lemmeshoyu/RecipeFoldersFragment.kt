package edu.uw.peihsi5.lemmeshoyu

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.*
import com.bumptech.glide.Glide
import edu.uw.peihsi5.lemmeshoyu.database.Folder
import edu.uw.peihsi5.lemmeshoyu.viewmodels.FolderViewModel

private const val TAG = ".RecipeFoldersFragment"

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class RecipeFoldersFragment : Fragment() {

    private lateinit var folderAdapter: FolderAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val rootView = inflater.inflate(R.layout.fragment_recipe_folders, container, false)

        folderAdapter = FolderAdapter(requireContext())

        val viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application))
            .get(FolderViewModel::class.java)

        val folderObserver = Observer<List<Folder>> {
            folderAdapter.submitList(it)
        }
        viewModel.allFolders?.observe(viewLifecycleOwner, folderObserver)

        // TODO NOTE:
        // viewModel.insertFolder(Folder("testFolder2", "https://spoonacular.com/recipeImages/716429-556x370.jpg"))

        val recycler: RecyclerView = rootView.findViewById(R.id.folders_recyclerview)
        recycler.adapter = folderAdapter
        recycler.layoutManager = GridLayoutManager(activity, 2)

        return rootView

    }

    inner class FolderAdapter(val context: Context) : ListAdapter<Folder, FolderAdapter.ViewHolder>(FolderDiffCallback()) {

        /**
         * A ViewHolder that stores the information about the folder
         */
        inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
            // store all movie information in the view holder
            val folderImage: ImageView = view.findViewById(R.id.item_image)
            val folderTextView: TextView = view.findViewById(R.id.item_name)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val inflatedView = LayoutInflater.from(parent.context).inflate( R.layout.folder_list_item, parent, false)
            return ViewHolder(inflatedView)
        }

        /**
         * Bind the data to the given holder, including the image of the folder and the folder name
         **/
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val theItem: Folder = getItem(position)

            Log.v(TAG, theItem.folderImageUrl)
            // load image
            Glide.with(context)
                .load(theItem.folderImageUrl)
                .error(R.drawable.error_image)
                .into(holder.folderImage)

            // load folder name
            holder.folderTextView.text = theItem.folderName

            // TODO add event listener on each folder
        }

    }

    inner class FolderDiffCallback: DiffUtil.ItemCallback<Folder>() {
        override fun areItemsTheSame(oldItem: Folder, newItem: Folder): Boolean {
            return oldItem.folderName == newItem.folderName
        }

        override fun areContentsTheSame(oldItem: Folder, newItem: Folder): Boolean {
            return oldItem == newItem
        }
    }
}