package com.example.learningdevelopment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.example.learningdevelopment.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import androidx.core.content.edit
import androidx.core.view.isVisible
import androidx.lifecycle.whenCreated

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null

    private val binding
        get() = _binding ?: throw IllegalStateException("ActivityMainBinding can't be null!")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Описание текущей темы
        val darkThemeText = "${ContextCompat.getString(this, R.string.dark_theme)}"
        val lightThemeText = "${ContextCompat.getString(this, R.string.light_theme)}"

        // Загружаем сохраненную тему
        val prefs = getSharedPreferences("settings", MODE_PRIVATE)
        val isDark = prefs.getBoolean("dark_theme", false)

        //Переменная для дефолтного цвета фона в светлой теме. Записывает в себя одно значение либо в data, resourceId и тд
        val typedValue = TypedValue()

        AppCompatDelegate.setDefaultNightMode(
            if (isDark) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )
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
                binding.rgBackgroundColor.check(prefs.getInt("rgBackgroundColorCheckedId", -1))
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


        //Описание темы
        binding.tvText.text = if (isDark) darkThemeText else lightThemeText

        //слушатель на CheckBox
        binding.cbDarkTheme.setOnCheckedChangeListener { _, checked ->
            prefs.edit {
                putBoolean("dark_theme", checked)
            }
            if (checked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.tvText.text = darkThemeText
                Toast.makeText(this, "$darkThemeText is on", Toast.LENGTH_SHORT).show()
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.tvText.text = lightThemeText
                Toast.makeText(this, "$lightThemeText is on", Toast.LENGTH_SHORT).show()
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
                prefs.edit { putInt("rgBackgroundColorCheckedId", checkedId) }
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
    }
}
