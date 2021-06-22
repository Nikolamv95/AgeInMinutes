package com.example.ageinminutes

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.ageinminutes.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnDatePicker.setOnClickListener { view ->
            // On click pass view to the function
            clickDatePicker(view)
        }
    }

    // Main logic function
    private fun clickDatePicker(view: View) {
        // Create instance of calendar
        val myCalendar = Calendar.getInstance()

        // Get the current information which we will pass
        // to the datePicker as default information
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val dayOfMonth = myCalendar.get(Calendar.DAY_OF_MONTH)

        // Create DatePickedDialog and save it to dpd
        val dpd = DatePickerDialog(this, // Values which comes as input from the date picked dialog
            DatePickerDialog.OnDateSetListener { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                // TODO: Implement the logic
                val selectedDate = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
                binding.tvSelectedDate.text = selectedDate

                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                val theDate = sdf.parse(selectedDate)

                try {
                    val selectedDateInMinutes = theDate!!.time / 60000
                    val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                    val currentDateToMinutes = currentDate!!.time / 60000
                    val differenceInMinutes = currentDateToMinutes - selectedDateInMinutes
                    binding.tvSelectedDateInMinutes.text = differenceInMinutes.toString()

                }catch (e: IllegalStateException){
                        Toast.makeText(this, "Skip this operation", Toast.LENGTH_SHORT).show()
                }

            }
            // Pass the default info
            , year, month, dayOfMonth)

        // Restrict the maxDate to current date - day before
        dpd.datePicker.maxDate = Date().time - 86400000
        // show the result
        dpd.show()
    }
}