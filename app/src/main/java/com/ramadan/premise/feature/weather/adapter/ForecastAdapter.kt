package com.ramadan.premise.feature.weather.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ramadan.premise.R
import com.ramadan.premise.domain.entity.WeatherInfo
import com.ramadan.premise.util.AppConst
import kotlinx.android.synthetic.main.activity_weather.*
import kotlinx.android.synthetic.main.item_forecast.view.*
import javax.inject.Inject

class ForecastAdapter @Inject constructor() : ListAdapter<WeatherInfo, ForecastAdapter.WeatherViewHolder>(WeatherDiffCallback()) {

    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_forecast, parent, false)
        return WeatherViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    inner class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindTo(weatherInfo: WeatherInfo) {
            itemView.temperatureTextView.text = AppConst.TEMPREATURE.plus(weatherInfo.temperature.toString())
            itemView.humidityTextView.text = AppConst.HUMIDITY.plus(weatherInfo.humidity.toString())
            itemView.pressureTextView.text = AppConst.PRESSURE.plus(weatherInfo.pressure.toString())
            itemView.weatherStatusTextView.text = AppConst.WEATHER_STATUS.plus(weatherInfo.weatherStatus)
            Glide.with(context).load(weatherInfo.weatherIcon).into(itemView.weatherIcon)
        }
    }
}

class WeatherDiffCallback : DiffUtil.ItemCallback<WeatherInfo>() {
    override fun areItemsTheSame(oldItem: WeatherInfo, newItem: WeatherInfo): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: WeatherInfo, newItem: WeatherInfo): Boolean {
        return oldItem == newItem
    }
}
