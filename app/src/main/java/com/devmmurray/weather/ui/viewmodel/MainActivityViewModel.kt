package com.devmmurray.weather.ui.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.devmmurray.weather.data.model.DailyForecasts
import com.devmmurray.weather.data.model.HourlyForecasts
import com.devmmurray.weather.data.model.Weather
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

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

    fun getWeather(lat: Double, lon: Double, units: String = "imperial") {
        getWeatherFromBaseViewModel(lat, lon, units)
    }

    fun getWeatherEntities() {
        viewModelScope.launch {
            val weather = repository.getCurrentWeather()
            _currentWeather.postValue(weather)
            val daily = repository.getDailyForecasts()
            _dailyForecasts.postValue(daily)
            val hourly = repository.getHourlyForecasts()
            _hourlyForecasts.postValue(hourly)
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun transformTimeStamp(utcTime: Long, flag: TimeFlag): String {
        val timeStamp = Date(
            TimeUnit.MILLISECONDS
                .convert(utcTime, TimeUnit.SECONDS)
        ).time

        val correctFormat = when (flag) {
            TimeFlag.FULL -> SimpleDateFormat("E, MMMM d hh:mm a")
            TimeFlag.DAY -> SimpleDateFormat("EEEE")
            TimeFlag.HOUR -> SimpleDateFormat("hh a")
        }

        return correctFormat.format(timeStamp)
    }


}