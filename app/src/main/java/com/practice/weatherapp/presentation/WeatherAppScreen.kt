package com.practice.weatherapp.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.practice.weatherapp.presentation.ui.theme.DarkBlue
import com.practice.weatherapp.presentation.ui.theme.DeepBlue
import com.practice.weatherapp.presentation.ui.theme.WeatherAppTheme

@Composable
fun WeatherAppScreen(
    state: WeatherState
) {
    WeatherAppTheme {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(DarkBlue)
            ) {
                WeatherCard(
                    state = state,
                    backgroundColor = DeepBlue
                )
                Spacer(modifier = Modifier.height(16.dp))
                WeatherForecast(state = state)
            }
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            state.error?.let { error ->
                Text(
                    text = error,
                    color = Color.Red,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}