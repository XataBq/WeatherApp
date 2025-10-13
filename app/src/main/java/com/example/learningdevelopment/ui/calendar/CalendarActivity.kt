package com.example.learningdevelopment.ui.calendar

import android.R
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import com.example.learningdevelopment.databinding.ActivityCalendarBinding
import com.example.learningdevelopment.ui.main.MainActivity
import java.util.Calendar

class CalendarActivity : AppCompatActivity() {

    private var _binding: ActivityCalendarBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("ActivityCalendarBinding can't be null")

    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefs = getSharedPreferences("settings", MODE_PRIVATE)

        val savedMode = prefs.getInt("dark_theme", AppCompatDelegate.MODE_NIGHT_NO)
        AppCompatDelegate.setDefaultNightMode(savedMode)

        val isDark: Boolean =
            AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES

        val style = if(isDark) R.style.Theme_Holo_Dialog_NoActionBar else R.style.Theme_Holo_Light_Dialog_NoActionBar

        if (!isDark) {
            val savedBackgroundColor = prefs.getInt("bg_color", -1)
            if (savedBackgroundColor != -1) {
                binding.root.setBackgroundColor(
                    ContextCompat.getColor(
                        this@CalendarActivity,
                        savedBackgroundColor
                    )
                )
            } else {
                binding.root.setBackgroundColor(
                    ContextCompat.getColor(
                        this@CalendarActivity, com.example.learningdevelopment.R.color.white
                    )
                )
            }
        }

        binding.tvBirthDay.text = prefs.getString("birth_day", "Enter your birth date")
        binding.tvBirthTime.text = prefs.getString("birth_time", "Enter your birth time")

        val calendar = Calendar.getInstance()

        with(binding) {

            ibChangeBirthDay.setOnClickListener {
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH)
                val day = calendar.get(Calendar.DAY_OF_MONTH)

                val dialog = DatePickerDialog(
                    this@CalendarActivity,
                    style,
                    { _, chosenYear, chosenMonth, chosenDay ->
                        val dateText =
                            String.format("%02d.%02d.%04d", chosenDay, chosenMonth + 1, chosenYear)
                        tvBirthDay.text = dateText
                        prefs.edit { putString("birth_day", dateText) }
                    }, year, month, day
                )
                dialog.show()
            }

            ibChangeBirthTime.setOnClickListener {
                val hour = calendar.get(Calendar.HOUR)
                val minute = calendar.get(Calendar.MINUTE)

                val timeDialog = TimePickerDialog(
                    this@CalendarActivity,
                    style,
                    { _, chosenHour, chosenMinute ->
                        val timeText = String.format("%02d:%02d", chosenHour, chosenMinute)
                        tvBirthTime.text = timeText
                        prefs.edit { putString("birth_time", timeText) }
                    }, hour, minute, true
                )
                timeDialog.show()
            }

//            tvBirthday.text = dateOfBirth
//            dpBirthday.init(birthYear, birthMonth, birthDay, null)
//            dpBirthday.setOnDateChangedListener { view, birthYear, birthMonth, birthDay ->
//                dateOfBirth = createDateOfBirth(birthDay, birthMonth, birthYear)
//                tvBirthday.text = dateOfBirth
//            }

            btnGoToMainActivity.setOnClickListener {
                val intent = Intent(this@CalendarActivity, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
//        with(binding) {
//            prefs.edit {
//                putInt("birth_day", dpBirthday.dayOfMonth)
//                putInt("birth_month", dpBirthday.month)
//                putInt("birth_year", dpBirthday.year)
//                putString("date_of_birth", tvBirthday.text.toString())
//            }
//        }
    }

    private fun createDateOfBirth(birthDay: Int, birthMonth: Int, birthYear: Int): String {
        return "${birthDay / 10}${birthDay % 10}.${birthMonth / 10}${birthMonth % 10 + 1}.$birthYear"
    }
}