package com.gustavodmcarlos.finances.ui.weather

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gustavodmcarlos.finances.BuildConfig.WEATHER_API_KEY
import com.gustavodmcarlos.finances.databinding.FragmentWeatherBinding
import com.gustavodmcarlos.finances.ui.NetworkUtils
import com.gustavodmcarlos.finances.ui.weather.data.OpenWeather
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "WeatherFragment"

class WeatherFragment : Fragment() {

    private lateinit var weatherViewModel: WeatherViewModel
    private var _binding: FragmentWeatherBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

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
                    val cityName = response.body()?.city?.name
                    Log.d(TAG, cityName!!)
                    binding.textWeather.text = cityName
                }

                override fun onFailure(call: Call<OpenWeather?>, t: Throwable) {
                    Log.d(TAG, "Request error")
                }

            }
        )

//        const requestUrl = `${BASE_WEATHER_URL}lat=${latitude}&lon=${longitude}&appid=${WEATHER_API_KEY}&units=metric`;

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}