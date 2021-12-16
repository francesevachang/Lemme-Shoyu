package edu.uw.peihsi5.lemmeshoyu

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import java.io.ByteArrayOutputStream

/**
 * A simple [Fragment] subclass.
 * Use the [FridgeAddItemFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FridgeAddItemFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fridge_add_item, container, false)
    }


    fun openCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        var cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                this.imageResultReceiver(1, result.resultCode, data)
            }
        }
        cameraLauncher.launch(takePictureIntent)
    }

    fun imageResultReceiver(requestCode: Int, resultCode: Int, data: Intent?) {
        val REQUEST_IMAGE_CAPTURE = 1
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val imageBitmap: Bitmap = data?.extras?.get("data") as Bitmap

            val stream = ByteArrayOutputStream()
            val byteArray: ByteArray = stream.toByteArray() // store this
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)

//            rootView.findViewById<ImageView>(R.id.test_imageview).setImageBitmap(imageBitmap)
        }
    }
}