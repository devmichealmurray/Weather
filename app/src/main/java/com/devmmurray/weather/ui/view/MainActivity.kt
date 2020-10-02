package com.devmmurray.weather.ui.view

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.devmmurray.weather.R
import com.devmmurray.weather.data.model.DailyForecasts
import com.devmmurray.weather.data.model.HourlyForecasts
import com.devmmurray.weather.data.model.Weather
import com.devmmurray.weather.ui.viewmodel.MainActivityViewModel
import com.devmmurray.weather.ui.viewmodel.TimeFlag
import kotlinx.android.synthetic.main.activity_main.*


private const val TAG = "* * * MAIN ACTIVITY * * *"
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "* * On Create Called * * ")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel.getWeather(32.755489, -97.330765, "imperial")
        mainViewModel.getWeatherEntities()

        mainViewModel.currentWeather.observe(this, currentWeatherObserver)
        mainViewModel.dailyForecasts.observe(this, dailyForecastsObserver)
        mainViewModel.hourlyForecasts.observe(this, hourlyForecastsObserver)

    }

    private val currentWeatherObserver = Observer<Weather> {
        todaysDate.text = it.current?.time?.let { time ->
            mainViewModel.transformTimeStamp(time, TimeFlag.FULL)
        }

        currentTemp.text = it.current?.temp?.toInt().toString()
        currentDescription.text = it.current?.currentWeatherDescription?.mainDescription.toString()
        val feelsLike = it.current?.feels?.toInt().toString()
        todaysFeelsLike.text = "Feels Like $feelsLike"
    }

    private val dailyForecastsObserver = Observer<List<DailyForecasts>> {

    }

    private val hourlyForecastsObserver = Observer<List<HourlyForecasts>> {

    }
}