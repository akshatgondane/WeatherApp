package com.practice.weatherapp.data.repository

import com.practice.weatherapp.data.mappers.toWeatherInfo
import com.practice.weatherapp.data.remote.WeatherApi
import com.practice.weatherapp.domain.repository.WeatherRepository
import com.practice.weatherapp.domain.util.Resource
import com.practice.weatherapp.domain.weather.WeatherInfo
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi
) : WeatherRepository {
    override suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo> {
        return try {
            Resource.Success(
                data = api.getWeatherData(lat = lat, long = long).toWeatherInfo()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred")
        }
    }
}