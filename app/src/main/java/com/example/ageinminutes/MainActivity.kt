package com.example.ageinminutes

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        initViews()
    }

    private fun initViews() {
        binding.btnDatePicker.setOnClickListener { view ->
            // On click pass the view to the function
            showDatePicker()
        }
    }

    // Main logic function
    private fun showDatePicker() {
        // Create instance of calendar
        val myCalendar = Calendar.getInstance()

        // Get the current information which we will pass
        // to the datePicker as default information
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val dayOfMonth = myCalendar.get(Calendar.DAY_OF_MONTH)

        // Create DatePickedDialog
        DatePickerDialog(this,
            // Values which comes as input from the date picked dialog
            { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                val selectedDate = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
                binding.tvSelectedDate.text = selectedDate

                val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                val theDate = dateFormatter.parse(selectedDate) ?: return@DatePickerDialog

                try {
                    val selectedDateInMinutes = theDate.time / 60000
                    val currentDate =
                        dateFormatter.parse(dateFormatter.format(System.currentTimeMillis()))
                    val currentDateToMinutes = currentDate.time / 60000
                    val differenceInMinutes = currentDateToMinutes - selectedDateInMinutes
                    binding.tvSelectedDateInMinutes.text = differenceInMinutes.toString()
                } catch (e: IllegalStateException) {
                    Toast.makeText(this, "Skip this operation", Toast.LENGTH_SHORT).show()
                }

            }
            // Pass the default info
            , year, month, dayOfMonth)
            .apply {
                // Restrict the maxDate to current date - day before
                datePicker.maxDate = Date().time - 86400000
                // show the result
                show()
            }
    }
}