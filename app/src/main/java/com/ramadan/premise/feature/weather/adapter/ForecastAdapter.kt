package com.ramadan.premise.feature.weather.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ramadan.premise.core.common.AppConst
import com.ramadan.premise.databinding.ItemForecastBinding
import com.ramadan.premise.domain.entity.WeatherInfo
import javax.inject.Inject

class ForecastAdapter @Inject constructor() : ListAdapter<WeatherInfo, ForecastAdapter.WeatherViewHolder>(WeatherDiffCallback()) {

    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        context = parent.context
        val binding = ItemForecastBinding.inflate(LayoutInflater.from(context), parent, false)
        return WeatherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    inner class WeatherViewHolder(private val binding: ItemForecastBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindTo(weatherInfo: WeatherInfo) {
            binding.temperatureTextView.text = AppConst.TEMPREATURE.plus(weatherInfo.temperature.toString())
            binding.humidityTextView.text = AppConst.HUMIDITY.plus(weatherInfo.humidity.toString())
            binding.pressureTextView.text = AppConst.PRESSURE.plus(weatherInfo.pressure.toString())
            binding.weatherStatusTextView.text = AppConst.WEATHER_STATUS.plus(weatherInfo.weatherStatus)
            Glide.with(context).load(weatherInfo.weatherIcon).into(binding.weatherIcon)
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
