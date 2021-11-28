package com.gustavodmcarlos.finances.ui.weather

import com.gustavodmcarlos.finances.BuildConfig.WEATHER_API_KEY
import com.gustavodmcarlos.finances.ui.weather.data.OpenWeather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

public interface OpenWeatherService {
    @GET("data/2.5/forecast?appid=$WEATHER_API_KEY&units=metric")
    fun getWeatherData(
        @Query("lat") latitude: String?,
        @Query("lon") longitude: String?): Call<OpenWeather?>?
}