package com.devmmurray.weather.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.devmmurray.weather.data.model.DailyForecastEntity

@Dao
interface DailyForecastsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addDailyForecast(dailyForecast: DailyForecastEntity)


    @Query("SELECT * FROM daily_forecasts")
    suspend fun getDailyForecasts(): MutableList<DailyForecastEntity>

    @Query("DELETE FROM daily_forecasts")
    suspend fun deleteOldDailyForecasts()
}