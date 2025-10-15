package com.example.learningdevelopment.data.model

data class City(
    val city: String,
    val lat: Double,
    val lon: Double,
    var temperatureUnit: String? = null,
    var windSpeedUnit: String? = null,
    var temperature: Double? = null,
    var windSpeed: Double? = null,
)
