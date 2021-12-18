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

class DatePickerFragment(view: View) : DialogFragment(), DatePickerDialog.OnDateSetListener {

    val originalView: View = view

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current date as the default date in the picker
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        // Create a new instance of DatePickerDialog and return it
        return DatePickerDialog(requireContext(), this, year, month, day)
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        originalView.findViewById<TextView>(R.id.day_picked).text = "Expiration date: $month/$day/$year"
    }
}
