package com.example.weather.providers

import com.example.weather.data.RedisWeatherRepository
import com.example.weather.weatherUpdater
import io.ktor.server.application.*
import kotlinx.coroutines.launch

fun Application.configureClient() {
    val cityNames = arrayOf("sydney", "santiago", "auckland", "zürich", "london", "atlanta")
    val repository = RedisWeatherRepository()
    launch {
        weatherUpdater(repository)("sydney")
    }
}