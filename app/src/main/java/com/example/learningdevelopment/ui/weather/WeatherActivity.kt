package com.example.learningdevelopment.ui.weather

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learningdevelopment.data.consts.MOSCOW
import com.example.learningdevelopment.data.consts.REUTOV
import com.example.learningdevelopment.data.consts.SAINT_PETERSBURG
import com.example.learningdevelopment.data.consts.UFA
import com.example.learningdevelopment.data.model.City
import com.example.learningdevelopment.databinding.ActivityWeatherBinding
import com.example.learningdevelopment.ui.main.MainActivity

class WeatherActivity : AppCompatActivity() {
    private var _binding: ActivityWeatherBinding? = null
    val binding
        get() = _binding ?: throw IllegalStateException("ActivityCalendarBinding can't be null")

    private lateinit var adapter: WeatherAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cities: MutableList<City> =
            mutableListOf(
                MOSCOW,
                REUTOV,
                UFA,
                SAINT_PETERSBURG,
            )

        adapter = WeatherAdapter(cities)

        binding.rvWeather.layoutManager = LinearLayoutManager(this)
        binding.rvWeather.adapter = adapter

        binding.btnStartMainActivity.setOnClickListener {
            val intent = Intent(this@WeatherActivity, MainActivity::class.java)
            startActivity(intent)
        }

//        // Способ через ретрофит и коллы
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
        // Способ через корутины
//        lifecycleScope.launch {
//            try {
//                val posts = RetrofitInstance.api.getPostById()
//                Log.d("Retrofit", "Получено ${posts.size} постов")
//            } catch (e: Exception) {
//                Log.e("Retrofit", "Ошибка: ${e.message}")
//            }
//        }
    }
}
