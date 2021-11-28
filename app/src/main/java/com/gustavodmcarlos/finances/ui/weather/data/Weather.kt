package com.gustavodmcarlos.finances.ui.weather.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import javax.annotation.Generated

@Generated("jsonschema2pojo")
data class Weather(
    @SerializedName("description")
    @Expose
    var description: String?,

    @SerializedName("icon")
    @Expose
    var icon: String?
)