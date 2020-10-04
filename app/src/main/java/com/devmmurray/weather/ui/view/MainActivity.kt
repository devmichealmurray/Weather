package com.devmmurray.weather.ui.view

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.devmmurray.weather.R
import com.devmmurray.weather.data.model.DailyForecasts
import com.devmmurray.weather.data.model.HourlyForecasts
import com.devmmurray.weather.data.model.Weather
import com.devmmurray.weather.ui.adapter.WeatherRecycler
import com.devmmurray.weather.ui.viewmodel.MainActivityViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.current_weather_layout.*


private const val TAG = "* * * MAIN ACTIVITY * * *"

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "* * On Create Called * * ")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel.clearDatabase()
        mainViewModel.getWeather(32.755489, -97.330765, "imperial")

        mainViewModel.apply {
            currentWeather.observe(this@MainActivity, currentWeatherObserver)
            dailyForecasts.observe(this@MainActivity, dailyForecastsObserver)
            hourlyForecasts.observe(this@MainActivity, hourlyForecastsObserver)
            databaseReady.observe(
                this@MainActivity,
                Observer { mainViewModel.getWeatherEntities() })
        }


    }

    private val currentWeatherObserver = Observer<Weather> {
        todaysDate.text = it.current?.time
        currentTemp.text = it.current?.temp?.toString()
        currentDescription.text = it.current?.currentWeatherDescription?.mainDescription.toString()
        val feelsLike = it.current?.feels?.toString()
        todaysFeelsLike.text = "Feels Like $feelsLike"

        val icon = it.current?.currentWeatherDescription?.currentIcon
        val url = "http://openweathermap.org/img/wn/$icon.png"

        Log.d(TAG, "* * * * * $url * * * * *")
        Picasso.get()
            .load(url)
            .fit()
            .into(mainWeatherIcon)
    }

    private val dailyForecastsObserver = Observer<List<DailyForecasts>> {

    }

    private val hourlyForecastsObserver = Observer<List<HourlyForecasts>> {
        it?.let {
            Log.d(TAG, "========== ${it.size} =================")
            hourlyTempsRecycler.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = WeatherRecycler(it)
            }
        }
    }

}