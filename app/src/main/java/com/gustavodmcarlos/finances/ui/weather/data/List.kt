package com.gustavodmcarlos.finances.ui.weather.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import javax.annotation.Generated
import kotlin.collections.List

@Generated("jsonschema2pojo")
data class List(
    @SerializedName("dt")
    @Expose
    var dt: Int?,

    @SerializedName("main")
    @Expose
    var main: Main?,

    @SerializedName("weather")
    @Expose
    var weather: List<Weather>?,

    @SerializedName("wind")
    @Expose
    var wind: Wind?,

    @SerializedName("pop")
    @Expose
    var pop: Double?
)