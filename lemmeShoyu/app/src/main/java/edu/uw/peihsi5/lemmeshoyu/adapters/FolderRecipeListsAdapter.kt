package edu.uw.peihsi5.lemmeshoyu.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.uw.peihsi5.lemmeshoyu.R
import java.util.*


private const val TAG = ".FolderRecipeListsAdapter"

class FolderRecipeListsAdapter<T>(val context: Context,
                                  private val viewOnClickListenerInterface: ViewOnClickListenerInterface<T>,
                                  private val bindDataToViewHolderInterface: BindDataToViewHolderInterface<T>,
                                  private val deleteFromDatabaseInterface: DeleteFromDatabaseInterface<T>)
    : ListAdapter<T, FolderRecipeListsAdapter<T>.ViewHolder>(FolderDiffCallback()) {

    /**
     * A ViewHolder that stores the information about the folder or the recipe
     */
    inner class ViewHolder(val view: View): RecyclerView.ViewHolder(view), View.OnLongClickListener {

        val itemImage: ImageView = view.findViewById(R.id.item_image)
        val itemTextView: TextView = view.findViewById(R.id.item_name)
        var item: T? = null

        override fun onLongClick(p0: View?): Boolean {
            val animShakeFirstHalf: Animation = AnimationUtils.loadAnimation(context,  R.anim.shake_back_first_half)
            val animShakeForward: Animation = AnimationUtils.loadAnimation(context,  R.anim.shake_forward_full)
            val animShakeSecondHalf: Animation = AnimationUtils.loadAnimation(context,  R.anim.shake_back_second_half)

            // shaking animations
            val handler = Handler(Looper.myLooper()!!)
            val timer = Timer()
            timer.schedule(object : TimerTask() {
                override fun run() {
                    view.startAnimation(animShakeFirstHalf)

                    handler.postDelayed({
                        view.startAnimation(animShakeForward)
                        handler.postDelayed({
                            view.startAnimation(animShakeSecondHalf)
                        }, 400)
                    }, 200)
                }
            }, 0, 800)

            // disable click listener on the item
            view.setOnClickListener{ null }

            // show the remove icons and add listener
            val removeFolderButton = view.findViewById<ImageButton>(R.id.remove_folder_button)
            removeFolderButton.visibility = View.VISIBLE
            removeFolderButton.setOnClickListener{
                deleteFromDatabaseInterface.deleteFromDatabase(item!!)
                timer.cancel()
            }

            return true
        }
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

        // default: the remove folder button has no click listener and is invisible
        val removeFolderButton = holder.view.findViewById<ImageButton>(R.id.remove_folder_button)
        removeFolderButton.visibility = View.INVISIBLE
        removeFolderButton.setOnClickListener{ null }

        holder.view.setOnClickListener {
            viewOnClickListenerInterface.viewOnClickListener(theItem)
        }

        holder.view.setOnLongClickListener {
            holder.onLongClick(holder.view)
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


// Below are the interface and functions that need to be implemented by the users
interface BindDataToViewHolderInterface<T> {
    fun bindDataToViewHolder(item: T, holder: FolderRecipeListsAdapter<T>.ViewHolder)
}

interface ViewOnClickListenerInterface<T> {
    fun viewOnClickListener(item: T)
}

interface DeleteFromDatabaseInterface<T> {
    fun deleteFromDatabase(item: T)
}

