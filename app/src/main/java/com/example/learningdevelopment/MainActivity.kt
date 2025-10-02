package com.example.learningdevelopment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.example.learningdevelopment.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import androidx.core.content.edit

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null

    private val binding
        get() = _binding ?: throw IllegalStateException("ActivityMainBinding can't be null!")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityMainBinding.inflate(layoutInflater)

        //Описание текущей темы
        val darkThemeText = "${ContextCompat.getString(this, R.string.dark_theme)}"
        val lightThemeText = "${ContextCompat.getString(this, R.string.light_theme)}"

        // Загружаем сохраненную тему
        val prefs = getSharedPreferences("settings", MODE_PRIVATE)
        val isDark = prefs.getBoolean("dark_theme", false)

        if (isDark) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        setContentView(binding.root)

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

        //Установка текущего значения CheckBox
        binding.cbOnOff.isChecked = isDark

        //Описание темы
        binding.tvText.text = if (isDark) darkThemeText else lightThemeText

        //слушатель на CheckBox
        binding.cbOnOff.setOnCheckedChangeListener { _, checked ->
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
    }
}
