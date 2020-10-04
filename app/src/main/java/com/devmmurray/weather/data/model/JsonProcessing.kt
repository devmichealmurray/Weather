package com.devmmurray.weather.data.model

import android.util.Log
import retrofit2.Response

object JsonProcessing {

    private val timeStampProcessing: TimeStampProcessing = TimeStampProcessing()

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
            time = currentResponse?.time?.let { utcTime ->
                timeStampProcessing.transformTimeStamp(utcTime, TimeFlag.FULL)
            },
            sunrise = currentResponse?.sunrise,
            sunset = currentResponse?.sunset,
            temp = currentResponse?.temp?.toInt(),
            feels = currentResponse?.feels?.toInt(),
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
                lowTemp = it.dailyTemps?.lowTemp?.toInt(),
                highTemp = it.dailyTemps?.highTemp?.toInt()
            )

            val dailyFeelsLike = DailyForecastFeelsLikeEntity(
                dayTimeFeelsLike = it.dailyFeelsLike?.dayTimeFeelsLike?.toInt(),
                nighttimeFeelsLike = it.dailyFeelsLike?.nighttimeFeelsLike?.toInt()
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
                time = it.time?.let { utcTime ->
                    timeStampProcessing.transformTimeStamp(utcTime, TimeFlag.DAY)
                },
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
                    time = it.time?.let { utcTime ->
                        timeStampProcessing.transformTimeStamp(utcTime, TimeFlag.HOUR)
                    },
                    hourlyTemp = it.hourlyTemp?.toInt(),
                    hourlyFeelsLike = it.hourlyFeelsLike?.toInt(),
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