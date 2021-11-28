package com.gustavodmcarlos.finances.ui.weather

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.bumptech.glide.Glide
import com.gustavodmcarlos.finances.databinding.WeatherItemBinding
import java.text.SimpleDateFormat
import java.util.*

class WeatherItemAdapter(context: Context, item: ArrayList<WeatherItem>) :
    ArrayAdapter<WeatherItem?>(context, 0, item as List<WeatherItem?>) {

    private lateinit var binding: WeatherItemBinding

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val weatherItem: WeatherItem? = getItem(position)
        val inflater = LayoutInflater.from(context)

        binding = WeatherItemBinding.inflate(inflater, parent, false)

        if (weatherItem != null) {
            binding.date.text = formatTimestamp(weatherItem.timestamp!!)
            binding.descriptionWeather.text = weatherItem.description!!
            updateLogo(weatherItem.icon!!, parent)
            binding.temperatureValue.text = formatTemperature(weatherItem.temperature!!)
            binding.precipitationValue.text = formatPrecipitation(weatherItem.precipitation!!)
            binding.windValue.text = formatWindSpeed(weatherItem.windSpeed!!)
            binding.humidityValue.text = formatHumidity(weatherItem.humidity!!)
        }

        return binding.root
    }

    private fun updateLogo(type : String, parent: ViewGroup) {
        val iconUrl = "https://openweathermap.org/img/wn/$type@4x.png"
        Glide.with(parent)
            .load(iconUrl)
            .into(binding.icon)
    }

    private fun formatTimestamp(timestamp : Int) : String {
        val date = Date(timestamp.toLong() * 1000)
        return SimpleDateFormat("dd-MM-yy hh:mm", Locale.getDefault()).format(date)
    }

    private fun formatTemperature(temp : Double) : String {
        val temps = String.format("%.1f", temp)
        return "$temps ºC"
    }

    private fun formatPrecipitation(prec : Double) : String {
        val precs = String.format("%.0f", prec * 100)
        return "$precs%"
    }

    private fun formatWindSpeed(ws: Double) : String {
        val wss = String.format("%.1f", ws)
        return "$wss km/h"
    }

    private fun formatHumidity(hum: Int) : String {
        val hums = String.format("%d", hum)
        return "$hums%"
    }
}

