/**
 * Pacy Wu: I wrote the ChooseFolderDialogFragment to let the user choose the folder
 * to add the recipe to. Using it, the dialog will remain in its position when changing
 * the orientation of the phone. It also deals with errors when the user did not select
 * any folder.
 **/

package edu.uw.peihsi5.lemmeshoyu.dialogs

import edu.uw.peihsi5.lemmeshoyu.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import edu.uw.peihsi5.lemmeshoyu.database.Folder
import edu.uw.peihsi5.lemmeshoyu.viewmodels.FolderViewModel

private const val TAG = ".ChooseFolderDialogFragment"

class ChooseFolderDialogFragment(): DialogFragment() {

    /** Inflate the fragment view and add the required listener to the element. **/
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_choose_folder_dialog, container, false)

        // get all folder names
        var _folderNames: MutableList<String> = mutableListOf()
        val folderNames = _folderNames
        val viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application))
            .get(FolderViewModel::class.java)

        val folderObserver = Observer<List<Folder>> {
            _folderNames.clear()
            it.map { folder ->
                _folderNames.add(folder.folderName)
            }
        }
        viewModel.allFolders?.observe(viewLifecycleOwner, folderObserver)

        // put all folders into the dropdown list
        val adapter = ArrayAdapter(requireContext(), R.layout.choose_folder_list_item, folderNames)
        val dropdownAutocompleteTextView = rootView.findViewById<AutoCompleteTextView>(R.id.choose_folder_dropdown_autocomplete)
        dropdownAutocompleteTextView?.setAdapter(adapter)

        // add close button listener
        val closeButton = rootView.findViewById<ImageButton>(R.id.choose_folder_dialog_close_button)
        closeButton.setOnClickListener {
            this.dismiss()
        }

        // add done button listener - finish choosing the folder to add to
        val doneButton = rootView.findViewById<Button>(R.id.choose_folder_dialog_done_button)
        doneButton.setOnClickListener {
            val selectedFolder: String = dropdownAutocompleteTextView.text.toString()
            if (selectedFolder.isEmpty()) { // nothing is selected
                val errorTextView = rootView.findViewById<TextView>(R.id.choose_folder_dialog_error_msg)
                errorTextView.visibility = View.VISIBLE
            } else {
                // TODO insert the recipe to the selected folder
                this.dismiss()
            }
        }
        return rootView
    }
}