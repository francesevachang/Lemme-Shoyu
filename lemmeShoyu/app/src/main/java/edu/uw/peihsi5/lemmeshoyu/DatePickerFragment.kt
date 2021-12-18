/**
 * Frances Chang: I referred to the Android documentation and wrote the DatePickerFragment class for
 * the user to select an expiration date.
 */

package edu.uw.peihsi5.lemmeshoyu

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import java.util.*

private const val TAG = "DatePickerFragment"

/**
 * This is a date picker that allows the user to pick an expiration date and updates the picked
 * date in the add item diaglog fragment.
 */
class DatePickerFragment(view: View) : DialogFragment(), DatePickerDialog.OnDateSetListener {

    val originalView: View = view

    /**
     * Handles functionalities when the dialogue is created, including setting a default expiration
     * date.
     */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current date as the default date in the picker
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(requireContext(), this, year, month, day)
    }

    /**
     * Handles functionalities when a specific date is selected by the user, which include setting
     * a text field that lets the user see which date they selected.
     */
    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        originalView.findViewById<TextView>(R.id.day_picked).text = "${month + 1}/$day/$year"
    }
}
