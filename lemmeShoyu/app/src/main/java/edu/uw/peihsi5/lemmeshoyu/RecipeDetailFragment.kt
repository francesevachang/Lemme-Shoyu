package edu.uw.peihsi5.lemmeshoyu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import edu.uw.peihsi5.lemmeshoyu.dialogs.ChooseFolderDialogFragment
import edu.uw.peihsi5.lemmeshoyu.network.Recipe


class RecipeDetailFragment : Fragment() {



    private var recipe: Recipe? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args : RecipeDetailFragmentArgs by navArgs()
        recipe = args.recipe


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false)
        rootView.findViewById<TextView>(R.id.recipe_title).text = recipe!!.title
        val recipePhoto: ImageView = rootView.findViewById<ImageView>(R.id.recipe_photo)
        Glide.with(rootView).load(recipe!!.imagePath).into(recipePhoto)
        rootView.findViewById<FloatingActionButton>(R.id.add_recipe_to_folder_button).setOnClickListener{
            val dialog = ChooseFolderDialogFragment(recipe!!)
            dialog.show(requireActivity().supportFragmentManager, "Choose Folder Dialog")
        }
        return rootView
    }


}