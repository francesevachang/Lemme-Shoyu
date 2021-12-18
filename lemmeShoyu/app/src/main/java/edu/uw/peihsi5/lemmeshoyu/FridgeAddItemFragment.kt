/**
 * Frances Chang: I wrote the fragment in the My fridge activity that acts as a fridge storing
 * items with an expiration date created and specified by the user.
 */

package edu.uw.peihsi5.lemmeshoyu.dialogs

import android.app.ActionBar
import edu.uw.peihsi5.lemmeshoyu.R
import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Bitmap
import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import edu.uw.peihsi5.lemmeshoyu.database.my_fridge_database.Ingredient
import edu.uw.peihsi5.lemmeshoyu.viewmodels.MyFridgeViewModel
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import edu.uw.peihsi5.lemmeshoyu.DatePickerFragment
import edu.uw.peihsi5.lemmeshoyu.DatePickerFragmentDirections
import android.view.WindowManager
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import java.io.ByteArrayOutputStream

private const val TAG = "FridgeAddItemDialogFragment"

/**
 * This is a fragement that displays the "fridge" in which ingredients created by the user, their
 * expiration dates, and the corresponding photo uploaded by the user are displayed. There is an add
 * button for the user to add an item and a delete item for each ingredient for the user to delete
 * that ingredient.
 */
class FridgeAddItemFragment(): DialogFragment() {

    private lateinit var rootView: View
    var cameraLaucher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // There are no request codes
            val data: Intent? = result.data
            this.imageResultReceiver(1, result.resultCode, data)
        }
    }
    private lateinit var imageByteArray: ByteArray

    /** Handles functionalities when the fragment is created */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    /**
     * Handles functionalities when the view of the fragment is created, including setting event
     * handlers for the buttons in the fragment.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_fridge_add_item, container, false)

        // add close button listener to close pop up dialog
        val closeButton = rootView.findViewById<ImageButton>(R.id.fridge_add_item_dialog_close_btn)
        closeButton.setOnClickListener {
            this.dismiss()
        }

        val takePhotoButton = rootView.findViewById<ImageButton>(R.id.fridge_add_ingredient_take_pic_button)
        takePhotoButton.setOnClickListener{ this.openCamera() }

        val pickDateButton = rootView.findViewById<Button>(R.id.fridge_pick_date_btn)
        pickDateButton.setOnClickListener {
            // pop up dialog of date picker
            val dialog = DatePickerFragment(rootView)
            dialog.show(requireActivity().supportFragmentManager, "datePicker")
        }

        val doneButton = rootView.findViewById<Button>(R.id.fridge_add_item_dialog_done_btn)
        doneButton.setOnClickListener {
            val itemName = rootView.findViewById<TextInputEditText>(R.id.fridge_add_item_dialog_name_text).text.toString().trim()
            if (itemName.isNotEmpty()) {
                val viewModel = ViewModelProvider(
                    this,
                    ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
                ).get(MyFridgeViewModel::class.java)

                val day = rootView.findViewById<TextView>(R.id.day_picked).text

                viewModel.insert(
                    Ingredient(
                        itemName = itemName,
                        expireMonth = day.substring(0, 2).toInt(),
                        expireDay = day.substring(3, 5).toInt(),
                        expireYear = day.substring(6).toInt(),
                        ingredientImage = imageByteArray
                    )
                ) { }
                this.dismiss()
            } else {
                val errorTextView = rootView.findViewById<TextView>(R.id.fridge_add_item_dialog_error_msg)
                errorTextView.visibility = View.VISIBLE
            }
        }

        return rootView
    }

    /** Change dialog width and height **/
    override fun onResume() {
        super.onResume()
        val params: ViewGroup.LayoutParams = dialog!!.window!!.attributes
        params.width = RelativeLayout.LayoutParams.MATCH_PARENT
        params.height = RelativeLayout.LayoutParams.WRAP_CONTENT
        dialog!!.window!!.attributes = params as WindowManager.LayoutParams
    }

    /** Open android's camera **/
    fun openCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraLaucher.launch(takePictureIntent)
    }

    /** After the user takes the photo, store the image to database and attach it to the image view. **/
    fun imageResultReceiver(requestCode: Int, resultCode: Int, data: Intent?) {
        val REQUEST_IMAGE_CAPTURE = 1
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val imageBitmap: Bitmap = data?.extras?.get("data") as Bitmap

            val stream = ByteArrayOutputStream()
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)

            val ingredientPhoto = rootView.findViewById<ImageView>(R.id.fridge_add_ingredient_photo)
            ingredientPhoto.setImageBitmap(imageBitmap)
            ingredientPhoto.visibility = View.VISIBLE

            imageByteArray = stream.toByteArray()

            rootView.findViewById<ImageButton>(R.id.fridge_add_ingredient_take_pic_button).visibility = View.GONE
        }
    }
}