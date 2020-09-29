package com.devmmurray.weather.data.repository

import com.devmmurray.weather.data.api.WeatherApiService
import com.devmmurray.weather.data.model.WeatherDTO
import retrofit2.Response


private const val API_KEY = "6f2f1d47ba30bfd187ed7ec88224312c"

object ApiRepo {

    suspend fun getWeatherOneCall(
        lat: Double, lon: Double, units: String): Response<WeatherDTO> {
        return WeatherApiService.apiClient.weatherOneCall(
            lat = lat,
            lon = lon,
            units = units,
            apiKey = API_KEY
        )
    }

}