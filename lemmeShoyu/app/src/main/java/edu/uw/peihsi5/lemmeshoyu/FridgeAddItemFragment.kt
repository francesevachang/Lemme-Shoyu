package edu.uw.peihsi5.lemmeshoyu.dialogs

import edu.uw.peihsi5.lemmeshoyu.R
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import edu.uw.peihsi5.lemmeshoyu.database.Folder
import edu.uw.peihsi5.lemmeshoyu.database.my_fridge_database.Ingredient
import edu.uw.peihsi5.lemmeshoyu.viewmodels.FolderViewModel
import edu.uw.peihsi5.lemmeshoyu.viewmodels.MyFridgeViewModel
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import java.io.ByteArrayOutputStream

private const val TAG = "FridgeAddItemDialogFragment"

class FridgeAddItemFragment(): DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val rootView = inflater.inflate(R.layout.fragment_fridge_add_item, container, false)

        val closeButton = rootView.findViewById<ImageButton>(R.id.fridge_add_item_dialog_close_btn)
        closeButton.setOnClickListener {
            this.dismiss()
        }

        val doneButton = rootView.findViewById<Button>(R.id.fridge_add_item_dialog_done_btn)
        doneButton.setOnClickListener {
            val itemName = rootView.findViewById<TextInputEditText>(R.id.fridge_add_item_dialog_text1).text.toString().trim()
            val expDate = rootView.findViewById<TextInputEditText>(R.id.fridge_add_item_dialog_text2).text.toString().trim()
            if (itemName.isNotEmpty() && expDate.isNotEmpty()) { // TODO: error handling of date format
                val viewModel = ViewModelProvider(
                    this,
                    ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
                ).get(MyFridgeViewModel::class.java)

                viewModel.insert(
                    Ingredient(
                        itemName = itemName,
                        expireMonth = expDate.substring(0, 2).toInt(),
                        expireDay = expDate.substring(3, 5).toInt(),
                        expireYear = expDate.substring(6).toInt())
                ) {
//                    this.insertExceptionHandler()
                }

                this.dismiss()
            } else {
                val errorTextView = rootView.findViewById<TextView>(R.id.fridge_add_item_dialog_error_msg)
                errorTextView.visibility = View.VISIBLE
            }

        }

        return rootView
    }

    fun openCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data: Intent? = result.data
                this.imageResultReceiver(1, result.resultCode, data)
            }
        }
        resultLauncher.launch(takePictureIntent)
    }

    fun imageResultReceiver(requestCode: Int, resultCode: Int, data: Intent?) {
        val REQUEST_IMAGE_CAPTURE = 1
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap

            val stream = ByteArrayOutputStream()
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)

//            rootView.findViewById<ImageView>(R.id.test_imageview).setImageBitmap(imageBitmap)
        }
    }
}