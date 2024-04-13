package com.practice.weatherapp.presentation

sealed class WeatherIntent {
    object InitializationIntent : WeatherIntent()
}