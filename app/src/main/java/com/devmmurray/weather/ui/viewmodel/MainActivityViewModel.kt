package com.devmmurray.weather.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.devmmurray.weather.data.model.DailyForecastEntity
import com.devmmurray.weather.data.model.HourlyForecastEntity
import com.devmmurray.weather.data.model.WeatherEntity
import com.devmmurray.weather.data.repository.ApiRepo
import kotlinx.coroutines.launch

private const val TAG = "ViewModel"

class MainActivityViewModel(application: Application) : BaseViewModel(application) {


    private fun addCurrentWeather(weather: WeatherEntity) {
        viewModelScope.launch {
            repository.addCurrentWeather(weather)
        }
    }

    private fun addHourlyWeather(hourlyWeather: HourlyForecastEntity) {
        viewModelScope.launch {
            repository.addHourlyForecast(hourlyWeather)
        }
    }

    private fun addDailyWeather(dailyWeather: DailyForecastEntity) {
        viewModelScope.launch {
            repository.addDailyForecast(dailyWeather)
        }
    }

    fun getWeather(lat: Double, lon: Double, units: String = "imperial") {
        callToOpenWeather(lat, lon, units)
    }



    private fun callToOpenWeather(lat: Double, lon: Double, units: String = "imperial") {
        Log.d(TAG, "- - - - Call To Open Weather Called - - - - - ")
        viewModelScope.launch {
            try {
                val result = ApiRepo
                    .getWeatherOneCall(lat, lon, units)
                Log.d(TAG, "Result = ${result.toString()}")
                if (result.isSuccessful) {
                    val currentWeather = parseForCurrentWeather(result)
                    addCurrentWeather(currentWeather as WeatherEntity)

                    val dailyForecast = parseForDailyForecast(result)
                    addDailyWeather(dailyForecast as DailyForecastEntity)

                    val hourlyForecast = parseForHourlyForecast(result)
                    addHourlyWeather(hourlyForecast as HourlyForecastEntity)

                } else {
                    Log.d(TAG, "======== RESULT NOT SUCCESSFUL =======")
                }

            } catch (e: Exception) {
                Log.d(TAG, "+++++++ ERROR ${e.localizedMessage} ++++++++++++")
                Log.d(TAG, "+++++++ ERROR ${e.message} ++++++++++++")
                Log.d(TAG, "+++++++ ERROR ${e.printStackTrace()} ++++++++++++")
            }
        }
    }
}