package com.devmmurray.weather.data.model

class Weather(
    val uid: Long?,
    val timeZoneOffset: Long?,
    val current: CurrentWeather?
)

class CurrentWeather(
    val time: Long?,
    val sunrise: Long?,
    val sunset: Long?,
    val temp: Double?,
    val feels: Double?,
    val humidity: Int?,
    val windSpeed: Double?,
    val currentWeatherDescription: CurrentWeatherDescription?
)

class CurrentWeatherDescription(
    val currentId: Int?,
    val mainDescription: String?,
    val description: String?,
    val currentIcon: String?
)




class HourlyForecasts(
    val uid: Long?,
    val time: Long?,
    val hourlyTemp: Double?,
    val hourlyFeelsLike: Double?,
    val hourlyWeather: HourlyForecastWeather?
)

class HourlyForecastWeather(
    val hourlyId: Int?,
    val mainForecast: String?,
    val forecastDescription: String?,
    val forecastIcon: String?
)




class DailyForecasts(
    val uid: Long?,
    val time: Long?,
    val sunrise: Long?,
    val sunset: Long?,
    val dailyTemps: DailyForecastTemps?,
    val dailyFeelsLike: DailyForecastFeelsLike?,
    val dailyWeather: DailyForecastWeather?
)

class DailyForecastTemps(
    val lowTemp: Double?,
    val highTemp: Double?
)

class DailyForecastFeelsLike(
    val dayTimeFeelsLike: Double?,
    val nighttimeFeelsLike: Double?
)

class DailyForecastWeather(
    val dailyId: Int?,
    val mainForecast: String?,
    val forecastDescription: String?,
    val forecastIcon: String?
)
