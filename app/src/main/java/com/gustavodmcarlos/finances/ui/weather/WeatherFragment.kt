package com.gustavodmcarlos.finances.ui.weather

import android.Manifest
import android.annotation.SuppressLint
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
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.gustavodmcarlos.finances.PermissionUtils
import com.gustavodmcarlos.finances.ui.MapsActivity

import java.lang.reflect.Type
import android.location.LocationManager
import androidx.constraintlayout.motion.widget.Debug.getLocation

import androidx.core.content.ContextCompat.getSystemService
import android.widget.Toast











private const val TAG = "WeatherFragment"

class WeatherFragment : Fragment() {

    private lateinit var weatherViewModel: WeatherViewModel
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var _binding: FragmentWeatherBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
//    private val weatherData: OpenWeather? = null TODO delete line

    @SuppressLint("MissingPermission")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        weatherViewModel =
            ViewModelProvider(this).get(WeatherViewModel::class.java)

        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        val root: View = binding.root

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)

        // get weather data
        val weatherInfo = loadData()
        updateUI(weatherInfo!!)

        enableMyLocation()

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location : Location? ->
                // Got last known location. In some rare situations this can be null.
                Log.d(TAG, "Teste location")
                // Got last known location. In some rare situations this can be null.
                if (location == null) {
                    Toast.makeText(context, "Unable to find location.", Toast.LENGTH_SHORT).show()
                    Log.e(TAG, "location is null")
                } else {
                    val lat = location.latitude.toString()
                    val lon = location.longitude.toString()
                    Log.d(TAG, "Got location $lat $lon")
                    requestData(lat, lon)
                }
            }

        return root
    }

    fun requestData(lat : String, lon : String) {
        val retrofitClient = NetworkUtils
            .getRetrofitInstance(BASE_WEATHER_URL)

        val endpoint = retrofitClient.create(OpenWeatherService::class.java)

        val callback = endpoint.getWeatherData(lat, lon)

        callback?.enqueue(
            object : Callback<OpenWeather?> {
                override fun onResponse(
                    call: Call<OpenWeather?>,
                    response: Response<OpenWeather?>
                ) {
                    updateWeatherInfo(response.body())
                }

                override fun onFailure(call: Call<OpenWeather?>, t: Throwable) {
                    Log.d(TAG, "Request error")
                }

            }
        )
    }

    private fun enableMyLocation() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            PermissionUtils.requestPermission(
                activity as AppCompatActivity, LOCATION_PERMISSION_REQUEST_CODE,
                Manifest.permission.ACCESS_FINE_LOCATION, true
            )
        }
    }

    fun updateWeatherInfo(newData : OpenWeather?) {
        if (newData == null) {
            Log.e(TAG, "Error updating weather info, newData is null")
            return
        }
        // load storage
        var weatherData = loadData()
        if (weatherData == null) {
            Log.d(TAG, "weatherData is null, creating empty instance")
            weatherData = WeatherInfo(mutableMapOf(), null)
        }


        // merge with newData
        val cityId = newData.city?.id
        val cityName = newData.city?.name
        val country = newData.city?.country
        val newHistory = newData.list

        weatherData.lastCity = cityId

        if (!weatherData.cities.containsKey(cityId)) {
            weatherData.cities[cityId!!] = CityWeatherData(cityName!!, country!!, mutableMapOf())
        }

        val city = weatherData.cities[cityId]!!

        for (h in newHistory!!) {
            if (!city.history.contains(h.dt)) {
                city.history[h.dt!!] = WeatherItem(h.dt, h.main?.temp, h.main?.humidity,
                    h.weather?.get(0)?.description, h.weather?.get(0)?.icon, h.wind?.speed, h.pop)
            }
        }

        // save storage
        saveData(weatherData)
        updateUI(weatherData)
    }

    fun updateUI(weatherInfo : WeatherInfo) {
        val weatherItems = arrayListOf<WeatherItem>()

        if (weatherInfo.lastCity == null) {
            return
        }

        val city = weatherInfo.cities[weatherInfo.lastCity]
        val cityName = city?.cityName
        val country = city?.country

        binding.weatherTitle.text = "$cityName $country"

        val history = city?.history

        for (h in history!!) {
            weatherItems.add(h.value)
        }

        weatherItems.sortByDescending { it.timestamp }

        for (i in weatherItems) {
//            Log.d(TAG, i.timestamp!!.toString())
        }

        val adapter = WeatherItemAdapter(requireContext(), weatherItems)
//        adapter.clear()
//        adapter.addAll(weatherItems);
        val listView = binding.weatherItemList
        listView.adapter = adapter
    }

    fun loadData(): WeatherInfo? {
        val sharedPref = activity?.getSharedPreferences("weatherData", MODE_PRIVATE)
        val gson = Gson()

        if (sharedPref != null) {
            val type: Type = object : TypeToken<WeatherInfo?>() {}.type
            val json = sharedPref.getString("data", null)
            val weatherInfo = gson.fromJson<Any>(json, type) as WeatherInfo?
            if (weatherInfo == null) {
                // if the array list is empty
                // creating a new array list.
                Log.e(TAG, "Failed to load weather data")
            }
            Log.d(TAG, "Loaded weather data")
            return weatherInfo
        } else {
            Log.e(TAG, "Failed to load weather data")
        }
        return null
    }

    fun saveData(weatherInfo: WeatherInfo?) {
//                    val data = response.body()
//                    val cityName = response.body()?.city?.name
//                    Log.d(TAG, cityName!!)
//                    binding.textWeather.text = cityName
        val sharedPref = activity?.getSharedPreferences("weatherData", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = gson.toJson(weatherInfo)

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

    companion object {
        private const val BASE_WEATHER_URL = "https://api.openweathermap.org/"
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }
}