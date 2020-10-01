package com.devmmurray.weather.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.devmmurray.weather.data.database.RoomDatabaseClient
import com.devmmurray.weather.data.model.*
import com.devmmurray.weather.data.repository.DatabaseRepo
import retrofit2.Response

open class BaseViewModel(application: Application) : AndroidViewModel(application) {

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

    fun parseForCurrentWeather(result: Response<WeatherDTO>): Any? {
        val currentResponse = result.body()?.current
        var currentWeatherDescription: CurrentWeatherDescriptionEntity? = null

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

        Log.d(
            "*** Weather ***",
            "* * * Current Forecast = ${current.currentWeatherDescription} * * *"
        )

        return WeatherEntity(
            timeZoneOffset = result.body()?.timeZoneOffset,
            current = current
        )
    }


    fun parseForDailyForecast(result: Response<WeatherDTO>): DailyForecastEntity? {
        val dailyResponse = result.body()?.dailyForecasts
        var dailyForecast: DailyForecastEntity? = null
        dailyResponse?.forEach {

            val dailyForecastTemps = DailyForecastTempsEntity(
                lowTemp = it.dailyTemps?.lowTemp,
                highTemp = it.dailyTemps?.highTemp
            )

            val dailyFeelsLike = DailyForecastFeelsLikeEntity(
                dayTimeFeelsLike = it.dailyFeelsLike?.dayTimeFeelsLike,
                nighttimeFeelsLike = it.dailyFeelsLike?.nighttimeFeelsLike
            )

            var dailyWeather: DailyForecastWeatherEntity? = null
            it.dailyWeather?.forEach { weather ->
                dailyWeather = DailyForecastWeatherEntity(
                    dailyId = weather.dailyId,
                    mainForecast = weather.mainForecast,
                    forecastDescription = weather.forecastDescription,
                    forecastIcon = weather.forecastIcon
                )
            }

            dailyForecast = DailyForecastEntity(
                time = it.time,
                sunrise = it.sunrise,
                sunset = it.sunset,
                dailyTemps = dailyForecastTemps,
                dailyFeelsLike = dailyFeelsLike,
                dailyWeather = dailyWeather
            )

            Log.d("*** Daily ***", "* * * Daily Forecast = ${dailyForecast?.time} * * *")

        }
        return dailyForecast
    }


    fun parseForHourlyForecast(result: Response<WeatherDTO>): HourlyForecastEntity? {
        var hourlyForecast: HourlyForecastEntity? = null
        var hourlyForecastWeather: HourlyForecastWeatherEntity? = null

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
            Log.d(
                "*** Weather ***",
                "* * * Hourly Forecast = ${hourlyForecastWeather.toString()} * * *"
            )

        }

        return hourlyForecast
    }

}

