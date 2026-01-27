package com.example.learningdevelopment.data.model

data class WeatherResponse(
    val latitude: Double,
    val longitude: Double,
    val current_weather_units: CurrentWeatherUnits,
    val current_weather: CurrentWeather,
)

data class CurrentWeatherUnits(
    val temperature: String,
    val windspeed: String,
)

data class CurrentWeather(
    val time: String,
    val temperature: Double,
    val windspeed: Double,
)
