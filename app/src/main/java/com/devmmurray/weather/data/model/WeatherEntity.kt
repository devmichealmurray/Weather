package com.devmmurray.weather.data.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "current_weather")
class WeatherEntity(
    @PrimaryKey(autoGenerate = true)
    val uid: Long = 0L,
    @ColumnInfo(name = "timezone_offset")
    val timeZoneOffset: Long?,
    @Embedded
    val current: CurrentWeatherEntity?
) {
    fun toWeatherObject() = Weather(
        uid, timeZoneOffset, current?.toCurrentWeatherObject()
    )
}

@Entity
class CurrentWeatherEntity(
    val time: String?,
    val sunrise: Long?,
    val sunset: Long?,
    val temp: Int?,
    val feels: Int?,
    val humidity: Int?,
    val windSpeed: Double?,
    @Embedded
    val currentWeatherDescription: CurrentWeatherDescriptionEntity?
) {
    fun toCurrentWeatherObject() = CurrentWeather(
        time, sunrise, sunset, temp, feels, humidity, windSpeed,
        currentWeatherDescription?.toWeatherDescriptionObject()
    )
}

@Entity
class CurrentWeatherDescriptionEntity(
    val currentId: Int?,
    val mainDescription: String?,
    val description: String?,
    val currentIcon: String?
) {
    fun toWeatherDescriptionObject() = CurrentWeatherDescription(
        currentId, mainDescription, description, currentIcon
    )
}


@Entity(tableName = "hourly_forecasts")
class HourlyForecastEntity(
    @PrimaryKey(autoGenerate = true)
    val uid: Long = 0L,
    @ColumnInfo(name = "time")
    val time: String?,
    @ColumnInfo(name = "temp")
    val hourlyTemp: Int?,
    @ColumnInfo(name = "feels")
    val hourlyFeelsLike: Int?,
    @Embedded
    val hourlyWeather: HourlyForecastWeatherEntity?

) {
    fun toHourlyForecastObject() = HourlyForecasts(
        uid, time, hourlyTemp, hourlyFeelsLike, hourlyWeather?.toHourlyForecastWeatherObject()
    )
}

@Entity
class HourlyForecastWeatherEntity(
    val hourlyId: Int?,
    val mainForecast: String?,
    val forecastDescription: String?,
    val forecastIcon: String?
) {
    fun toHourlyForecastWeatherObject() = HourlyForecastWeather(
        hourlyId, mainForecast, forecastDescription, forecastIcon
    )
}


@Entity(tableName = "daily_forecasts")
class DailyForecastEntity(
    @PrimaryKey(autoGenerate = true)
    val uid: Long = 0L,
    @ColumnInfo(name = "time")
    val time: String?,
    @ColumnInfo(name = "sunrise")
    val sunrise: Long?,
    @ColumnInfo(name = "sunset")
    val sunset: Long?,
    @Embedded
    val dailyTemps: DailyForecastTempsEntity?,
    @Embedded
    val dailyFeelsLike: DailyForecastFeelsLikeEntity?,
    @Embedded
    val dailyWeather: DailyForecastWeatherEntity?
) {
    fun toDailyForecastObject() = DailyForecasts(
        uid, time, sunrise, sunset, dailyTemps?.toDailyForecastTempsObject(),
        dailyFeelsLike?.toDailyForecastFeelsLikeObject(),
        dailyWeather?.toDailyForecastWeatherObject()
    )
}

@Entity
class DailyForecastTempsEntity(
    val lowTemp: Int?,
    val highTemp: Int?
) {
    fun toDailyForecastTempsObject() = DailyForecastTemps(
        lowTemp, highTemp
    )
}

@Entity
class DailyForecastFeelsLikeEntity(
    val dayTimeFeelsLike: Int?,
    val nighttimeFeelsLike: Int?
) {
    fun toDailyForecastFeelsLikeObject() = DailyForecastFeelsLike(
        dayTimeFeelsLike, nighttimeFeelsLike
    )
}

@Entity
class DailyForecastWeatherEntity(
    val dailyId: Int?,
    val mainForecast: String?,
    val forecastDescription: String?,
    val forecastIcon: String?
) {
    fun toDailyForecastWeatherObject() = DailyForecastWeather(
        dailyId, mainForecast, forecastDescription, forecastIcon
    )
}



























