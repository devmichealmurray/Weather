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
    val time: Long?,
    val sunrise: Long?,
    val sunset: Long?,
    val temp: Double?,
    val feels: Double?,
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
    val time: Long?,
    @ColumnInfo(name = "temp")
    val hourlyTemp: Double?,
    @ColumnInfo(name = "feels")
    val hourlyFeelsLike: Double?,
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
    val time: Long?,
    @ColumnInfo(name = "sunrise")
    val sunrise: Long?,
    @ColumnInfo(name = "sunset")
    val sunset: Long?,
    @Embedded
 //   @ColumnInfo(name = "daily_temps")
    val dailyTemps: DailyForecastTempsEntity?,
    @Embedded
 //   @ColumnInfo(name = "daily_feels")
    val dailyFeelsLike: DailyForecastFeelsLikeEntity?,
    @Embedded
 //   @ColumnInfo(name = "daily_weather")
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
    val lowTemp: Double?,
    val highTemp: Double?
) {
    fun toDailyForecastTempsObject() = DailyForecastTemps(
        lowTemp, highTemp
    )
}

@Entity
class DailyForecastFeelsLikeEntity(
    val dayTimeFeelsLike: Double?,
    val nighttimeFeelsLike: Double?
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



























