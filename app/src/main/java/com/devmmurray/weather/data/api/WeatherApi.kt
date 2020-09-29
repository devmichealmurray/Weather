package com.devmmurray.weather.data.api

import com.devmmurray.weather.data.model.WeatherDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query



// Current weather by geo location
// https://api.openweathermap.org/data/2.5/onecall?lat={lat}&lon={lon}&units={units}&exclude=minutely&appid={API key}

interface WeatherApi {

    @GET("data/2.5/onecall")
    suspend fun weatherOneCall(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String,
        @Query("apiKey") apiKey: String

    ): Response<WeatherDTO>

}