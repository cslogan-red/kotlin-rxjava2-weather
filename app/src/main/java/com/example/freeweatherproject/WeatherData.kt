package com.example.freeweatherproject

/**
 * Simple data class representing weather API response
 */
data class WeatherData (
    val currently: Currently? = null,
    val daily: List<Daily>? = null,
    val hourly: List<Hourly>? = null,
    val alerts: List<Any>? = null
)

