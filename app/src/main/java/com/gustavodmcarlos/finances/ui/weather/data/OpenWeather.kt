package com.gustavodmcarlos.finances.ui.weather.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import javax.annotation.Generated

@Generated("jsonschema2pojo")
data class OpenWeather(
    @SerializedName("list")
    @Expose
    var list: kotlin.collections.List<List>?,

    @SerializedName("city")
    @Expose
    var city: City?
)