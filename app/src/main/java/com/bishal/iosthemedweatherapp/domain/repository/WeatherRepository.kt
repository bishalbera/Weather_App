package com.bishal.iosthemedweatherapp.domain.repository

import com.bishal.iosthemedweatherapp.domain.utils.Resource
import com.bishal.iosthemedweatherapp.domain.weather.WeatherInfo

interface WeatherRepository {
    suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo>
}