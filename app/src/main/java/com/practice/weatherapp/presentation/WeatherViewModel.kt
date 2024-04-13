package com.practice.weatherapp.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practice.weatherapp.domain.location.LocationTracker
import com.practice.weatherapp.domain.repository.WeatherRepository
import com.practice.weatherapp.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val locationTracker: LocationTracker
) : ViewModel() {
    private val _state = MutableStateFlow(WeatherState())
    val state = _state.asStateFlow()

    val intent = Channel<WeatherIntent>(Channel.UNLIMITED)

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            intent.consumeAsFlow().collect {
                when(it) {
                    WeatherIntent.InitializationIntent -> handleInitializationIntent()
                }
            }
        }
    }

    private fun handleInitializationIntent() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true,
                    error = null
                )
            }
            locationTracker.getCurrentLocation()?.let { location ->
                Log.d("CustomTag", "lat: ${location.latitude}")
                Log.d("CustomTag", "lon: ${location.longitude}")
                when (val result =
                    repository.getWeatherData(location.latitude, location.longitude)) {
                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                weatherInfo = result.data,
                                isLoading = false,
                                error = null
                            )
                        }
                    }

                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                weatherInfo = null,
                                isLoading = false,
                                error = result.message
                            )
                        }
                    }
                }
            } ?: kotlin.run {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = "Couldn't retrieve location. Make sure to grant permission and enable GPS."
                    )
                }
            }
        }
    }
}