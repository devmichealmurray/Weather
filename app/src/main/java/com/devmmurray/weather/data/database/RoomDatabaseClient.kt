package com.devmmurray.weather.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.devmmurray.weather.data.model.DailyForecastEntity
import com.devmmurray.weather.data.model.HourlyForecastEntity
import com.devmmurray.weather.data.model.WeatherEntity

const val DATABASE_SCHEMA_VERSION = 1
const val DB_NAME = "weather-db"

@Database(
    version = DATABASE_SCHEMA_VERSION,
    entities = [
        WeatherEntity::class,
        HourlyForecastEntity::class,
        DailyForecastEntity::class
    ],
    exportSchema = false
)

abstract class RoomDatabaseClient : RoomDatabase() {

    abstract fun currentWeatherDAO(): CurrentWeatherDAO
    abstract fun hourlyForecastsDAO(): HourlyForecastsDAO
    abstract fun dailyForecastsDAO(): DailyForecastsDAO

    companion object {
        private var instance: RoomDatabaseClient? = null

        private fun createDatabase(context: Context): RoomDatabaseClient {
            return Room
                .databaseBuilder(context, RoomDatabaseClient::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }

        fun getDbInstance(context: Context): RoomDatabaseClient =
            (instance ?: createDatabase(context)).also {
                instance = it
            }
    }

}