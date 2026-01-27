package com.example.learningdevelopment.ui.weather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.learningdevelopment.R
import com.example.learningdevelopment.data.model.City
import com.example.learningdevelopment.data.remote.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeatherAdapter(
    private val cities: MutableList<City>,
) : RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {
    class WeatherViewHolder(
        itemView: View,
    ) : RecyclerView.ViewHolder(itemView) {
        val tvCity: TextView = itemView.findViewById(R.id.tvCityName)
        val tvTemp: TextView = itemView.findViewById(R.id.tvTemperature)
        val tvTempUnit: TextView = itemView.findViewById(R.id.tvTemperatureUnit)
        val tvWindSpeed: TextView = itemView.findViewById(R.id.tvWindSpeed)
        val tvWindUnit: TextView = itemView.findViewById(R.id.tvWindUnit)
        val btnRefresh: Button = itemView.findViewById(R.id.btnRefresh)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): WeatherViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_weather, parent, false)
        return WeatherViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: WeatherViewHolder,
        position: Int,
    ) {
        val city = cities[position]
        holder.tvCity.text = city.city
        holder.tvTemp.text = city.temperature?.let { "$it" } ?: "—"
        holder.tvTempUnit.text = city.temperatureUnit ?: "—"
        holder.tvWindSpeed.text = city.windSpeed?.let { "$it" } ?: "—"
        holder.tvWindUnit.text = city.windSpeedUnit ?: "—"
        holder.btnRefresh.setOnClickListener {
            updateWeather(city, position)
        }
    }

    override fun getItemCount(): Int = cities.size

    private fun updateWeather(
        city: City,
        position: Int,
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitInstance.api.getCurrentWeather(city.lat, city.lon)
                city.temperature = response.current_weather.temperature
                city.windSpeed = response.current_weather.windspeed
                city.temperatureUnit = response.current_weather_units.temperature
                city.windSpeedUnit = response.current_weather_units.windspeed
                withContext(Dispatchers.Main) {
                    notifyItemChanged(position)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
