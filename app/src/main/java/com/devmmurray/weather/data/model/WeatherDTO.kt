package com.devmmurray.weather.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherDTO(
    @Json(name = "timezone_offset") val timeZoneOffset: Long?,
    @Json(name = "current") val current: CurrentWeatherDTO?,
    @Json(name = "hourly") val hourlyForecasts: List<HourlyForecastsDTO>?,
    @Json(name = "daily") val dailyForecasts: List<DailyForecastsDTO>?
)

@JsonClass(generateAdapter = true)
data class CurrentWeatherDTO(
    @Json(name = "dt") val time: Long?,
    @Json(name = "sunrise") val sunrise: Long?,
    @Json(name = "sunset") val sunset: Long?,
    @Json(name = "temp") val temp: Double?,
    @Json(name = "feels_like") val feels: Double?,
    @Json(name ="humidity") val humidity: Int?,
    @Json(name = "wind_speed") val windSpeed: Double?,
    @Json(name = "weather") val weather: List<CurrentWeatherDescriptionDTO>?
)

@JsonClass(generateAdapter = true)
data class CurrentWeatherDescriptionDTO(
    @Json(name = "id") val currentId: Int?,
    @Json(name = "main") val mainDescription: String?,
    @Json(name = "description") val description: String?,
    @Json(name = "icon") val currentIcon: String?
)



@JsonClass(generateAdapter = true)
data class HourlyForecastsDTO(
    @Json(name = "dt") val time: Long?,
    @Json(name = "temp") val hourlyTemp: Double?,
    @Json(name = "feels_like") val hourlyFeelsLike: Double?,
    @Json(name = "weather") val hourlyWeather: List<HourlyForecastWeatherDTO>?
)

@JsonClass(generateAdapter = true)
data class HourlyForecastWeatherDTO(
    @Json(name = "id") val hourlyId: Int?,
    @Json(name = "main") val mainForecast: String?,
    @Json(name = "description") val forecastDescription: String?,
    @Json(name = "icon") val forecastIcon: String?
)



@JsonClass(generateAdapter = true)
data class DailyForecastsDTO(
    @Json(name = "dt") val time: Long?,
    @Json(name = "sunrise") val sunrise: Long?,
    @Json(name = "sunset") val sunset: Long?,
    @Json(name = "temp") val dailyTemps: List<DailyForecastTemps>?,
    @Json(name = "feels_like") val dailyFeelsLike: List<DailyForecastFeelsLikeDTO>?,
    @Json(name = "weather") val dailyWeather: List<DailyForecastWeatherDTO>
)

@JsonClass(generateAdapter = true)
data class DailyForecastTempsDTO(
    @Json(name = "min") val lowTemp: Double?,
    @Json(name = "max") val highTemp: Double?
)

@JsonClass(generateAdapter = true)
data class DailyForecastFeelsLikeDTO(
    @Json(name = "day") val dayTimeFeelsLike: Double?,
    @Json(name = "night") val nighttimeFeelsLike: Double?
)

@JsonClass(generateAdapter = true)
data class DailyForecastWeatherDTO(
    @Json(name = "id") val dailyId: Int?,
    @Json(name = "main") val mainForecast: String?,
    @Json(name = "description") val forecastDescription: String?,
    @Json(name = "icon") val forecastIcon: String?
)

