package com.example.freeweatherproject

/**
 * Simple data class representing location API response
 */
data class LocationData (val lat: String, val lng: String, val formattedAddr: String)