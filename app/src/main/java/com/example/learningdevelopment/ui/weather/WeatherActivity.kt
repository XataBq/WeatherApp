package com.example.learningdevelopment.ui.weather

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.learningdevelopment.data.remote.RetrofitInstance
import com.example.learningdevelopment.databinding.ActivityWeatherBinding
import com.example.learningdevelopment.ui.main.MainActivity
import kotlinx.coroutines.launch

class WeatherActivity : AppCompatActivity() {

    private var _binding: ActivityWeatherBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("ActivityCalendarBinding can't be null")

    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)

//       // Способ через ретрофит и коллы
//        RetrofitInstance.api.getPostById().enqueue(object : Callback<List<Post>> {
//            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
//                if (response.isSuccessful) {
//                    val post = response.body()
//                    if (post != null) {
//                        for (i in post)
//                            Log.d("Retrofit", "Post title: ${i.title}")
//                    }
//                } else {
//                    Log.e("Retrofit", "Server returned error ${response.code()}")
//                }
//            }

//            override fun onFailure(call: Call<List<Post>?>, t: Throwable) {
//                Log.e("Retrofit", "Network error: ${t.message}")
//            }
//        })

        lifecycleScope.launch {
            try {
                val posts = RetrofitInstance.api.getPostById()
                Log.d("Retrofit", "Получено ${posts.size} постов")
            } catch (e: Exception) {
                Log.e("Retrofit", "Ошибка: ${e.message}")
            }
        }

        binding.btnStartMainActivity.setOnClickListener {
            val intent = Intent(this@WeatherActivity, MainActivity::class.java)
            startActivity(intent)
        }

    }
}