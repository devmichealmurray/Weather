package com.devmmurray.weather.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devmmurray.weather.R
import com.devmmurray.weather.data.model.HourlyForecasts
import com.squareup.picasso.Picasso

class WeatherViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

   fun bindWeather(weather: HourlyForecasts) {
       val hour: TextView = view.findViewById(R.id.hour)
       val hourlyImage: ImageView = view.findViewById(R.id.hourlyImage)
       val hourlyTemp: TextView = view.findViewById(R.id.hourlyTemp)
       val hourlyHumidity: TextView = view.findViewById(R.id.hourlyHumidity)

       hour.text = weather.time
       hourlyTemp.text = weather.hourlyTemp.toString()
       hourlyHumidity.text = weather.hourlyWeather?.forecastDescription

       val icon = weather.hourlyWeather?.forecastIcon
       val url = "http://openweathermap.org/img/wn/$icon.png"

       Picasso.get()
           .load(url)
           .into(hourlyImage)

   }
}

class WeatherRecycler(private val list: List<Any>) : RecyclerView.Adapter<WeatherViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        return WeatherViewHolder(LayoutInflater
            .from(parent.context)
            .inflate(R.layout.hourly_temps_layout_item, parent, false))
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bindWeather(list[position] as HourlyForecasts)
    }

    override fun getItemCount(): Int = list.size
}