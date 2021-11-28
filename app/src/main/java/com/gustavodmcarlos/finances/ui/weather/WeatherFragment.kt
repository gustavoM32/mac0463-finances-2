package com.gustavodmcarlos.finances.ui.weather

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.gustavodmcarlos.finances.databinding.FragmentWeatherBinding
import com.gustavodmcarlos.finances.ui.NetworkUtils
import com.gustavodmcarlos.finances.ui.weather.data.OpenWeather
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import android.content.Context.MODE_PRIVATE

import android.content.SharedPreferences
import java.lang.reflect.Type


private const val TAG = "WeatherFragment"

class WeatherFragment : Fragment() {

    private lateinit var weatherViewModel: WeatherViewModel
    private var _binding: FragmentWeatherBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
//    private val weatherData: OpenWeather? = null TODO delete line

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        weatherViewModel =
            ViewModelProvider(this).get(WeatherViewModel::class.java)

        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // get weather data

        val BASE_WEATHER_URL = "https://api.openweathermap.org/"

        val retrofitClient = NetworkUtils
            .getRetrofitInstance(BASE_WEATHER_URL)

        val endpoint = retrofitClient.create(OpenWeatherService::class.java)
        val callback = endpoint.getWeatherData("0.0", "0.0")

        callback?.enqueue(
            object : Callback<OpenWeather?> {
                override fun onResponse(
                    call: Call<OpenWeather?>,
                    response: Response<OpenWeather?>
                ) {
                    // TODO: need to merge current data with new data
                    // load storage
                    // merge with response.body
                    // save storage

                    saveData(response.body())
                }

                override fun onFailure(call: Call<OpenWeather?>, t: Throwable) {
                    Log.d(TAG, "Request error")
                }

            }
        )


//        const requestUrl = `${BASE_WEATHER_URL}lat=${latitude}&lon=${longitude}&appid=${WEATHER_API_KEY}&units=metric`;

        return root
    }

    fun updateUi() {
        val weatherData = loadData()

    }

    fun loadData(): OpenWeather? {
        val sharedPref = activity?.getSharedPreferences("weatherData", MODE_PRIVATE)
        val gson = Gson()

        if (sharedPref != null) {
            val type: Type = object : TypeToken<OpenWeather?>() {}.type
            val json = sharedPref.getString("data", null)
            val weatherData = gson.fromJson<Any>(json, type) as OpenWeather?
            if (weatherData == null) {
                // if the array list is empty
                // creating a new array list.
                Log.e(TAG, "Failed to load weather data")
            }
            Log.d(TAG, "Loaded weather data")
            return weatherData
        } else {
            Log.e(TAG, "Failed to load weather data")
        }
        return null
    }

    fun saveData(weatherData: OpenWeather?) {
//                    val data = response.body()
//                    val cityName = response.body()?.city?.name
//                    Log.d(TAG, cityName!!)
//                    binding.textWeather.text = cityName
        val sharedPref = activity?.getSharedPreferences("weatherData", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = gson.toJson(weatherData)

        if (sharedPref != null) {
            with (sharedPref.edit()) {
                putString("data", json)
                apply()
            }
            Log.d(TAG, "Saved weather data")
        } else {
            Log.e(TAG, "Failed to save weather data")
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}