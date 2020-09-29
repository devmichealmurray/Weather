package com.devmmurray.weather.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.devmmurray.weather.data.model.HourlyForecastEntity

@Dao
interface HourlyForecastsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addHourlyForecast(forecast: HourlyForecastEntity)

    @Query("SELECT * FROM hourly_forecasts")
    suspend fun getHourlyForecasts(): MutableList<HourlyForecastEntity>
}