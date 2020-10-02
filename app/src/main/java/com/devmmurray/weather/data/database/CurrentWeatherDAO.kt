package com.devmmurray.weather.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.devmmurray.weather.data.model.WeatherEntity

@Dao
interface CurrentWeatherDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCurrentWeather(currentWeather: WeatherEntity)

    @Query("SELECT * FROM current_weather")
    suspend fun getCurrentWeather(): WeatherEntity

    @Query("DELETE FROM current_weather")
    suspend fun deleteOldWeather()
}