package edu.uw.peihsi5.lemmeshoyu.dialogs

import edu.uw.peihsi5.lemmeshoyu.R
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
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.provider.MediaStore
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import java.io.ByteArrayOutputStream

private const val TAG = "FridgeAddItemDialogFragment"

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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        rootView = inflater.inflate(R.layout.fragment_fridge_add_item, container, false)

        // add close button listener to close pop up dialog
        val closeButton = rootView.findViewById<ImageButton>(R.id.fridge_add_item_dialog_close_btn)
        closeButton.setOnClickListener {
            this.dismiss()
        }

        val takePhotoButton = rootView.findViewById<ImageButton>(R.id.fridge_add_ingredient_take_pic_button)
        takePhotoButton.setOnClickListener{ this.openCamera() }

        val doneButton = rootView.findViewById<Button>(R.id.fridge_add_item_dialog_done_btn)
        doneButton.setOnClickListener {
            val itemName = rootView.findViewById<TextInputEditText>(R.id.fridge_add_item_dialog_name_text).text.toString().trim()
            val expDate = rootView.findViewById<TextInputEditText>(R.id.fridge_add_item_dialog_date_text).text.toString().trim()
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
                        expireYear = expDate.substring(6).toInt(),
                        ingredientImage = imageByteArray
                    )
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
        cameraLaucher.launch(takePictureIntent)
    }

    fun imageResultReceiver(requestCode: Int, resultCode: Int, data: Intent?) {
        val REQUEST_IMAGE_CAPTURE = 1
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap

            val stream = ByteArrayOutputStream()
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)

            val ingredientPhoto = rootView.findViewById<ImageView>(R.id.fridge_add_ingredient_photo)
            ingredientPhoto.setImageBitmap(imageBitmap)
            ingredientPhoto.visibility = View.VISIBLE


            imageByteArray = stream.toByteArray()

            rootView.findViewById<ImageButton>(R.id.fridge_add_ingredient_take_pic_button).visibility = View.GONE
        }
    }

//    inner class ImageBitmapString {
//        @TypeConverter
//        fun BitMapToString(bitmap :Bitmap): String? {
//            val baos: ByteArrayOutputStream = ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
//            val b: ByteArray =baos.toByteArray();
//            val temp: String = Base64.encodeToString(b, Base64.DEFAULT);
//            if(temp == null)
//            {
//                return null;
//            }
//            else
//                return temp;
//        }
//
//        @TypeConverter
//        fun StringToBitMap(encodedString: String ): Bitmap? {
//            try {
//                var encodeByte: ByteArray = Base64.decode(encodedString,Base64.DEFAULT);
//                val  bitmap: Bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size);
//                if(bitmap == null)
//                {
//                    return null;
//                }
//                else {
//                    return bitmap
//                }
//            } catch( e: Exception) {
////                e.getMessage();
//                return null;
//            }
//        }

}