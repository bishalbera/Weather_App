package com.bishal.iosthemedweatherapp.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.bishal.iosthemedweatherapp.data.mappers.toWeatherInfo
import com.bishal.iosthemedweatherapp.data.remote.WeatherApi
import com.bishal.iosthemedweatherapp.domain.repository.WeatherRepository
import com.bishal.iosthemedweatherapp.domain.utils.Resource
import com.bishal.iosthemedweatherapp.domain.weather.WeatherInfo
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor (private val api : WeatherApi
): WeatherRepository  {
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo> {
        return try {
            Resource.Success(
                data = api.getWeatherData(
                    lat = lat,
                    long = long
                ).toWeatherInfo()
            )
        }catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred")
        }
    }
}