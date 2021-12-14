package edu.uw.peihsi5.lemmeshoyu

import android.graphics.Color
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
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import edu.uw.peihsi5.lemmeshoyu.database.Folder
import edu.uw.peihsi5.lemmeshoyu.viewmodels.FolderViewModel

private const val TAG = ".AddFolderDialogFragment"

class AddFolderDialogFragment(): DialogFragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val rootView = inflater.inflate(R.layout.fragment_add_folder_dialog, container, false)

        val closeButton = rootView.findViewById<ImageButton>(R.id.add_folder_dialog_close_button)
        closeButton.setOnClickListener {
            this.dismiss()
        }

        val doneButton = rootView.findViewById<Button>(R.id.add_folder_dialog_done_button)
        doneButton.setOnClickListener {
            val folderName = rootView.findViewById<TextInputEditText>(R.id.add_folder_dialog_input_text).text.toString().trim()
            if (folderName.isNotEmpty()) {
                val viewModel = ViewModelProvider(
                    this,
                    ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application))
                    .get(FolderViewModel::class.java)

                viewModel.insertFolder(Folder(folderName, null)) {
                    this.insertExceptionHandler()
                }

                this.dismiss()
            } else {
                val errorTextView = rootView.findViewById<TextView>(R.id.add_folder_dialog_error_msg)
                errorTextView.visibility = View.VISIBLE
            }

        }

        return rootView
    }

    private fun insertExceptionHandler() {
        Handler(Looper.getMainLooper()).post {
            val root = requireActivity().findViewById<RelativeLayout>(R.id.root_activity_main)
            Snackbar.make(root, "The folder name already exists. Please enter another folder name", Snackbar.LENGTH_LONG)
                .show()
        }
    }






}