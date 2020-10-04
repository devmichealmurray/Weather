package com.devmmurray.weather.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.devmmurray.weather.data.model.DailyForecasts
import com.devmmurray.weather.data.model.HourlyForecasts
import com.devmmurray.weather.data.model.Weather
import kotlinx.coroutines.launch

private const val TAG = "ViewModel"

class MainActivityViewModel(application: Application) : BaseViewModel(application) {

    private val _currentWeather by lazy { MutableLiveData<Weather>() }
    val currentWeather: LiveData<Weather> get() = _currentWeather

    private val _dailyForecasts
            by lazy { MutableLiveData<List<DailyForecasts>>() }
    val dailyForecasts: LiveData<List<DailyForecasts>> get() = _dailyForecasts

    private val _hourlyForecasts
            by lazy { MutableLiveData<List<HourlyForecasts>>() }
    val hourlyForecasts: LiveData<List<HourlyForecasts>> get() = _hourlyForecasts


    fun clearDatabase() {
        deleteAllWeather()
    }

    fun getWeather(lat: Double, lon: Double, units: String = "imperial") {
        getWeatherFromBaseViewModel(lat, lon, units)

    }

    fun getWeatherEntities() {
        viewModelScope.launch {
            try {
                val weather = repository.getCurrentWeather()
                _currentWeather.postValue(weather)
                val daily = repository.getDailyForecasts()
                _dailyForecasts.postValue(daily)
                val hourly = repository.getHourlyForecasts()
                _hourlyForecasts.postValue(hourly)
            } catch (e: Exception) {
                // Exception Code Body
                Log.d(TAG, "* * * *  getWeatherEntites Failed * * * * ")
            }
        }
    }

}