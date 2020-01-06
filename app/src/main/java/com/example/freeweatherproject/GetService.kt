package com.example.freeweatherproject

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface GetService {

    @GET("location/{location}")
    fun getLocation(@Path("location") location: String) : Observable<LocationData>

    @GET("weather/lookup/{lat},{lng}")
    fun getWeather(@Path("lat") lat: Double, @Path( "lng") lng: Double) : Observable<WeatherData>
}