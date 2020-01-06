package com.example.freeweatherproject

/**
 * Simple data class representing location API response
 */
data class LocationData (val lat: Double, val lng: Double, val formattedAddr: String)