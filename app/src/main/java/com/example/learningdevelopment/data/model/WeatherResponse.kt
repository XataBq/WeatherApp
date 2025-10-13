package com.example.learningdevelopment.data.model

data class WeatherResponse(
    val latitude: Double,
    val longitude: Double,
    val currentWeatherUnits: CurrentWeatherUnits,
    val currentWeather: CurrentWeather
)

data class CurrentWeatherUnits(
    val time: String,
    val temperature: String,
    val windSpeed: String,
)

data class CurrentWeather(
    val time: String,
    val temperature: String,
    val windSpeed: String
)