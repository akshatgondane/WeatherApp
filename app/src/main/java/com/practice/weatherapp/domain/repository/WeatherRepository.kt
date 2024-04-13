package com.practice.weatherapp.domain.repository

import com.practice.weatherapp.domain.util.Resource
import com.practice.weatherapp.domain.weather.WeatherInfo

interface WeatherRepository {
    suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo>
}