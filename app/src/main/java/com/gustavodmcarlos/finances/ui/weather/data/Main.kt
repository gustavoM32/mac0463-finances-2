package com.gustavodmcarlos.finances.ui.weather.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import javax.annotation.Generated

@Generated("jsonschema2pojo")
data class Main(
    @SerializedName("temp")
    @Expose
    var temp: Double?,

    @SerializedName("humidity")
    @Expose
    var humidity: Int?
)