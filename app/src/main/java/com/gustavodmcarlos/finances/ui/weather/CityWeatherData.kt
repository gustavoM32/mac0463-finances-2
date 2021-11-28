package com.gustavodmcarlos.finances.ui.weather

class CityWeatherData(
    var cityName: String,
    var country: String,
    val history: MutableMap<Int, WeatherItem>) {}
