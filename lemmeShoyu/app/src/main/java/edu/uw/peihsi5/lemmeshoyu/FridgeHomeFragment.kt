package edu.uw.peihsi5.lemmeshoyu

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import edu.uw.peihsi5.lemmeshoyu.database.my_fridge_database.Ingredient
import edu.uw.peihsi5.lemmeshoyu.dialogs.AddFolderDialogFragment
import edu.uw.peihsi5.lemmeshoyu.dialogs.FridgeAddItemFragment
import edu.uw.peihsi5.lemmeshoyu.viewmodels.FolderViewModel
import edu.uw.peihsi5.lemmeshoyu.viewmodels.MyFridgeViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 * Use the [FridgeHomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FridgeHomeFragment : Fragment() {
    private val TAG = "FridgeHomeFragment"
    private lateinit var adapter: FridgeListAdapter
    private lateinit var viewModel: MyFridgeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_fridge_home, container, false)

        adapter = FridgeListAdapter()
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        ).get(MyFridgeViewModel::class.java)

        val resultsObserver = Observer<List<Ingredient>> {
            adapter.submitList(it)
            Log.v(TAG, it.toString())
        }
        viewModel.allIngredients?.observe(viewLifecycleOwner, resultsObserver)
//        viewModel.insert(Ingredient(itemName = "2 carrots", expireYear = 2021, expireMonth = 12, expireDay = 30)) { Log.v(TAG, "troubles") }


        val recycler = rootView.findViewById<RecyclerView>(R.id.fridge_list)
        recycler.layoutManager = LinearLayoutManager(activity)
        recycler.adapter = adapter


        // add ingredient
        rootView.findViewById<FloatingActionButton>(R.id.floating_add_ingredient_button).setOnClickListener {
            // pop up dialog to allow the user take photo of the ingredient and add expired date
            val dialog = FridgeAddItemFragment()
            dialog.show(requireActivity().supportFragmentManager, TAG)
        }


//        implement add button
//        rootView.findViewById<ImageView>(R.id.search_icon).setOnClickListener { searchMovie(rootView) }

        return rootView
    }

    inner class FridgeListAdapter() : ListAdapter<Ingredient, FridgeListAdapter.ViewHolder>(FridgeDiffCallback()) {
        /**
         * A ViewHolder that takes in a [view] and holds data for it, in this case, the view is the state
         * result item for a state
         */
        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//            val fridgeListItem: RelativeLayout = view.findViewById<RelativeLayout>(R.id.fridge_list_item)
            val itemDeleteBtn: ImageButton = view.findViewById<ImageButton>(R.id.delete_fridge_item_btn)
            val itemName: TextView = view.findViewById<TextView>(R.id.fridge_list_item_name)
            val itemExpDate: TextView = view.findViewById<TextView>(R.id.fridge_list_item_exp_date)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val inflatedView = LayoutInflater.from(parent.context).inflate(
                R.layout.fridge_list_item,
                parent,
                false
            )
            return ViewHolder(inflatedView)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = getItem(position)
//            holder.itemDeleteBtn.setOnClickListener {
//                // delete item
//            }
            holder.itemName.text = item.itemName
            holder.itemExpDate.text = "${item.expireMonth}/${item.expireDay}/${item.expireYear}"
        }
    }

    inner class FridgeDiffCallback: DiffUtil.ItemCallback<Ingredient>() {
        override fun areItemsTheSame(oldItem: Ingredient, newItem: Ingredient): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Ingredient, newItem: Ingredient): Boolean {
            return oldItem == newItem
        }
    }
}