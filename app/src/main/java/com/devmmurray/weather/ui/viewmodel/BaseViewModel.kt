package com.devmmurray.weather.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.devmmurray.weather.data.database.RoomDatabaseClient
import com.devmmurray.weather.data.model.DailyForecastEntity
import com.devmmurray.weather.data.model.HourlyForecastEntity
import com.devmmurray.weather.data.model.JsonProcessing
import com.devmmurray.weather.data.model.WeatherEntity
import com.devmmurray.weather.data.repository.ApiRepo
import com.devmmurray.weather.data.repository.DatabaseRepo
import kotlinx.coroutines.launch

private const val TAG = "BaseViewModel"

open class BaseViewModel(application: Application) : AndroidViewModel(application) {

    private val _databaseReady by lazy { MutableLiveData<Boolean>() }
    val databaseReady: LiveData<Boolean> get() = _databaseReady

    val repository: DatabaseRepo

    init {
        val currentWeatherDAO = RoomDatabaseClient
            .getDbInstance(application).currentWeatherDAO()
        val hourlyForecastsDAO = RoomDatabaseClient
            .getDbInstance(application).hourlyForecastsDAO()
        val dailyForecastsDAO = RoomDatabaseClient
            .getDbInstance(application).dailyForecastsDAO()

        repository = DatabaseRepo(currentWeatherDAO, hourlyForecastsDAO, dailyForecastsDAO)
    }

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

    fun deleteAllWeather() {
        deleteDatabaseInfo()
    }

    private fun deleteDatabaseInfo() {
        viewModelScope.launch {
            repository.deleteOldWeatherData()
            repository.deleteOldDailyForecasts()
            repository.deleteOldHourlyForecasts()
        }
    }


    fun getWeatherFromBaseViewModel(lat: Double, lon: Double, units: String = "imperial") {
        callToOpenWeather(lat, lon, units)
    }

    private fun callToOpenWeather(lat: Double, lon: Double, units: String = "imperial") {
        viewModelScope.launch {
            try {
                val result = ApiRepo
                    .getWeatherOneCall(lat, lon, units)

                if (result.isSuccessful) {
                    val currentWeather = JsonProcessing.parseForCurrentWeather(result)
                    addCurrentWeather(currentWeather as WeatherEntity)

                    val dailyForecast = JsonProcessing.parseForDailyForecast(result)
                    addDailyWeather(dailyForecast as DailyForecastEntity)

                    val hourlyForecast = JsonProcessing.parseForHourlyForecast(result)
                    addHourlyWeather(hourlyForecast as HourlyForecastEntity)

                } else {
                    Log.d(TAG, "======== RESULT NOT SUCCESSFUL =======")
                }

            } catch (e: Exception) {
                Log.d(TAG, "+++++++ ERROR ${e.localizedMessage} ++++++++++++")
                Log.d(TAG, "+++++++ ERROR ${e.message} ++++++++++++")
                Log.d(TAG, "+++++++ ERROR ${e.printStackTrace()} ++++++++++++")
            }

            _databaseReady.postValue(true)
        }
    }



}

