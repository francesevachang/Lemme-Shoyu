package edu.uw.peihsi5.lemmeshoyu

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class FolderRecipeListsAdapter<T>(val context: Context,
                                  private val viewOnClickListenerInterface: ViewOnClickListenerInterface<T>,
                                  private val bindDataToViewHolderInterface: BindDataToViewHolderInterface<T>)
    : ListAdapter<T, FolderRecipeListsAdapter<T>.ViewHolder>(FolderDiffCallback()) {

    /**
     * A ViewHolder that stores the information about the folder
     */
    inner class ViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        // store all movie information in the view holder
        val itemImage: ImageView = view.findViewById(R.id.item_image)
        val itemTextView: TextView = view.findViewById(R.id.item_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate( R.layout.folder_list_item, parent, false)
        return ViewHolder(inflatedView)
    }

    /**
     * Bind the data to the given holder, including the image of the folder and the folder name
     **/
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val theItem: T = getItem(position)

        bindDataToViewHolderInterface.bindDataToViewHolder(theItem, holder)

        holder.view.setOnClickListener {
            viewOnClickListenerInterface.viewOnClickListener(theItem)
        }
    }
}

class FolderDiffCallback<T>: DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }
}

interface BindDataToViewHolderInterface<T> {
    fun bindDataToViewHolder(item: T, holder: FolderRecipeListsAdapter<T>.ViewHolder)
}

interface ViewOnClickListenerInterface<T> {
    fun viewOnClickListener(item: T)
}

