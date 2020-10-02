package com.devmmurray.weather.data.repository

import com.devmmurray.weather.data.database.CurrentWeatherDAO
import com.devmmurray.weather.data.database.DailyForecastsDAO
import com.devmmurray.weather.data.database.HourlyForecastsDAO
import com.devmmurray.weather.data.model.DailyForecastEntity
import com.devmmurray.weather.data.model.HourlyForecastEntity
import com.devmmurray.weather.data.model.WeatherEntity

class DatabaseRepo(
    private val currentWeatherDataSource: CurrentWeatherDAO,
    private val hourlyWeatherDataSource: HourlyForecastsDAO,
    private val dailyWeatherDataSource: DailyForecastsDAO
) {

    suspend fun addCurrentWeather(weather: WeatherEntity) =
        currentWeatherDataSource.addCurrentWeather(weather)

    suspend fun getCurrentWeather() =
        currentWeatherDataSource.getCurrentWeather().toWeatherObject()

    suspend fun deleteOldWeatherData() =
        currentWeatherDataSource.deleteOldWeather()



    suspend fun addHourlyForecast(forecast: HourlyForecastEntity) =
        hourlyWeatherDataSource.addHourlyForecast(forecast)

    suspend fun getHourlyForecasts() =
        hourlyWeatherDataSource.getHourlyForecasts().map { it.toHourlyForecastObject() }

    suspend fun deleteOldHourlyForecasts() =
        hourlyWeatherDataSource.deleteOldHourlyForecasts()



    suspend fun addDailyForecast(forecast: DailyForecastEntity) =
        dailyWeatherDataSource.addDailyForecast(forecast)

    suspend fun getDailyForecasts() =
        dailyWeatherDataSource.getDailyForecasts().map { it.toDailyForecastObject() }

    suspend fun deleteOldDailyForecasts() =
        dailyWeatherDataSource.deleteOldDailyForecasts()

}