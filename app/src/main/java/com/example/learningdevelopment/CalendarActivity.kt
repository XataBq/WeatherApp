package com.example.learningdevelopment

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.DatePicker
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import androidx.core.view.isVisible
import com.example.learningdevelopment.databinding.ActivityCalendarBinding

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
                    ContextCompat.getColor(this@CalendarActivity
                        , R.color.white)
                )
            }
        }

        val birthDay = prefs.getInt("birth_day", 1)
        val birthMonth = prefs.getInt("birth_month", 0)
        val birthYear = prefs.getInt("birth_year", 2000)
        var dateOfBirth = createDateOfBirth(birthDay, birthMonth, birthYear)

        with(binding) {
            tvBirthday.text = dateOfBirth
            dpBirthday.init(birthYear, birthMonth, birthDay, null)
            dpBirthday.setOnDateChangedListener { view, birthYear, birthMonth, birthDay ->
                dateOfBirth = createDateOfBirth(birthDay, birthMonth, birthYear)
                tvBirthday.text = dateOfBirth
            }

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
        with(binding) {
            prefs.edit {
                putInt("birth_day", dpBirthday.dayOfMonth)
                putInt("birth_month", dpBirthday.month)
                putInt("birth_year", dpBirthday.year)
                putString("date_of_birth", tvBirthday.text.toString())
            }
        }
    }

    private fun createDateOfBirth(birthDay: Int, birthMonth: Int, birthYear: Int): String {
        return "${birthDay / 10}${birthDay % 10}.${birthMonth / 10}${birthMonth % 10 + 1}.$birthYear"
    }
}