package com.devmmurray.weather.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devmmurray.weather.data.model.*
import com.devmmurray.weather.data.repository.ApiRepo
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {


    fun callToOpenWeather(lat: Double, lon: Double, units: String = "imperial") {
        viewModelScope.launch {
            try {
                val result = ApiRepo
                    .getWeatherOneCall(lat, lon, units)
                if (result.isSuccessful) {

                    var currentWeatherDescription: CurrentWeatherDescriptionEntity? = null
                    var hourlyForecast: HourlyForecastEntity? = null
                    var dailyForecast: DailyForecastEntity? = null
                    var hourlyForecastWeather: HourlyForecastWeatherEntity? = null

                    val currentResponse = result.body()?.current

                    result.body()?.current?.weather?.forEach {
                        currentWeatherDescription = CurrentWeatherDescriptionEntity(
                            currentId = it.currentId,
                            mainDescription = it.mainDescription,
                            description = it.description,
                            currentIcon = it.currentIcon
                        )
                    }

                    val current = CurrentWeatherEntity(
                        time = currentResponse?.time,
                        sunrise = currentResponse?.sunrise,
                        sunset = currentResponse?.sunset,
                        temp = currentResponse?.temp,
                        feels = currentResponse?.feels,
                        humidity = currentResponse?.humidity,
                        windSpeed = currentResponse?.windSpeed,
                        currentWeatherDescription = currentWeatherDescription

                    )
                    val currentWeather = WeatherEntity(
                        timeZoneOffset = result.body()?.timeZoneOffset,
                        current = current
                    )


                    result.body()?.hourlyForecasts?.forEach {
                        it.hourlyWeather?.forEach { hourly ->
                            hourlyForecastWeather = HourlyForecastWeatherEntity(
                                hourlyId = hourly.hourlyId,
                                mainForecast = hourly.mainForecast,
                                forecastDescription = hourly.forecastDescription,
                                forecastIcon = hourly.forecastIcon
                            )
                            hourlyForecast = HourlyForecastEntity(
                                time = it.time,
                                hourlyTemp = it.hourlyTemp,
                                hourlyFeelsLike = it.hourlyFeelsLike,
                                hourlyWeather = hourlyForecastWeather
                            )
                        }
                    }

                    result.body()?.dailyForecasts?.forEach {

                    }

                


            } else {
            // Throw Exception
        }

        } catch (e: Exception) {
            //Exception code body
        }
    }
}
}