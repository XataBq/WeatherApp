package com.example.learningdevelopment.ui.main

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import androidx.core.view.isVisible
import com.example.learningdevelopment.ui.calendar.CalendarActivity
import com.example.learningdevelopment.R
import com.example.learningdevelopment.ui.weather.WeatherActivity
import com.example.learningdevelopment.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null

    private val binding
        get() = _binding ?: throw IllegalStateException("ActivityMainBinding can't be null!")

    private lateinit var prefs: SharedPreferences

//    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
//        super.onRestoreInstanceState(savedInstanceState)
//        binding.cbDarkTheme.isChecked = false
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Описание текущей темы
        val darkThemeText = "${ContextCompat.getString(this, R.string.dark_theme)}"
        val lightThemeText = "${ContextCompat.getString(this, R.string.light_theme)}"

        // Загружаем сохраненную тему
        prefs = getSharedPreferences("settings", MODE_PRIVATE)
        val savedMode = prefs.getInt("dark_theme", AppCompatDelegate.MODE_NIGHT_NO)
        AppCompatDelegate.setDefaultNightMode(savedMode)

        //Переменная для дефолтного цвета фона в светлой теме. Записывает в себя одно значение либо в data, resourceId и тд
        val typedValue = TypedValue()

        val isDark: Boolean =
            AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES

        //Установка текущего значения CheckBox
        binding.cbDarkTheme.isChecked = isDark

        if (!isDark) {
            binding.rgBackgroundColor.isVisible = true
            val savedBackgroundColor = prefs.getInt("bg_color", -1)
            if (savedBackgroundColor != -1) {
                binding.root.setBackgroundColor(
                    ContextCompat.getColor(
                        this@MainActivity,
                        savedBackgroundColor
                    )
                )
                // Выбор чекбокса цвета фона в зависимости от фона
                when (prefs.getInt("bg_color", R.color.white)) {
                    R.color.purple_background -> binding.rbPurple.isChecked = true
                    R.color.white -> binding.rbWhite.isChecked = true
                    R.color.yellow_background -> binding.rbYellow.isChecked = true
                }
            } else {
                binding.rgBackgroundColor.check(binding.rbWhite.id)
                // Как вариант достать значение
//                theme.resolveAttribute(android.R.attr.windowBackground, typedValue, true)
                binding.root.setBackgroundColor(
                    ContextCompat.getColor(this@MainActivity, R.color.white)
//                    typedValue.data
                )
            }
        } else binding.rgBackgroundColor.isVisible = false

        binding.btnClick.setOnClickListener {
            val snackBar = Snackbar.make(
                binding.btnClick,
                "Hello android",
                Snackbar.LENGTH_INDEFINITE
            )
            snackBar.setActionTextColor(ContextCompat.getColor(this, R.color.blue))
            snackBar.setBackgroundTint(ContextCompat.getColor(this, R.color.red))
            snackBar.setAction("Agree", View.OnClickListener() {
                Toast.makeText(this, "Agreed!", Toast.LENGTH_LONG).show()
            }).show()
        }

        //слушатель на CheckBox
        binding.cbDarkTheme.setOnCheckedChangeListener { _, checked ->
            val newMode =
                if (checked) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO

            if (AppCompatDelegate.getDefaultNightMode() != newMode) {
                prefs.edit {
                    putInt("dark_theme", newMode)
                }
                AppCompatDelegate.setDefaultNightMode(newMode)
                if (checked) Toast.makeText(this, "$darkThemeText is on", Toast.LENGTH_SHORT).show()
                else Toast.makeText(this, "$lightThemeText is on", Toast.LENGTH_SHORT).show()
            }
        }

        //ToggleButton @tbLight
        binding.tbLight.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) Toast.makeText(this, "Lighting is on", Toast.LENGTH_SHORT).show()
            else Toast.makeText(this, "Lighting is off", Toast.LENGTH_SHORT).show()
        }

        //RadioButton RadioGroup @rgBackgorundColor @rbYellow @rbPurple
        if (!isDark) {
            binding.rgBackgroundColor.setOnCheckedChangeListener { group, checkedId ->
                val radioButton = group.findViewById<RadioButton>(checkedId)

                when (radioButton.text) {
                    "Purple" -> {
                        binding.root.setBackgroundColor(
                            ContextCompat.getColor(
                                this@MainActivity,
                                R.color.purple_background
                            )
                        )
                        prefs.edit {
                            putInt("bg_color", R.color.purple_background)
                        }
                    }

                    "Yellow" -> {
                        binding.root.setBackgroundColor(
                            ContextCompat.getColor(
                                this@MainActivity,
                                R.color.yellow_background
                            )
                        )
                        prefs.edit {
                            putInt("bg_color", R.color.yellow_background)
                        }
                    }

                    "White" -> {
                        binding.root.setBackgroundColor(
                            ContextCompat.getColor(
                                this@MainActivity,
                                R.color.white
                            )
                        )
                        prefs.edit {
                            putInt("bg_color", R.color.white)
                        }
                    }

                    else -> {
                        binding.root.setBackgroundColor(
                            ContextCompat.getColor(this@MainActivity, R.color.white)
                        )
                    }
                }
            }
        }

        binding.btnStartBirthdayActivity.setOnClickListener {
            val intent = Intent(
                this@MainActivity, CalendarActivity::class.java
            )
            startActivity(intent)
        }

        binding.btnStartWeatherActivity.setOnClickListener {
            val intent = Intent(
                this@MainActivity, WeatherActivity::class.java
            )
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        binding.tvBirthday.text = prefs.getString("birth_day", "__.__.____")
    }
}