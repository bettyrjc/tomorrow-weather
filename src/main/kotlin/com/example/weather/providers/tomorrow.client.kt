package com.example.weather.providers

import com.example.weather.data.RedisWeatherRepository
import com.example.weather.weatherUpdater
import io.ktor.server.application.*
import kotlinx.coroutines.*

fun Application.configureClient() {
    val cityNames = arrayOf("sydney", "santiago", "auckland", "zürich", "london", "atlanta")
    val repository = RedisWeatherRepository()

    fun CoroutineScope.startPeriodicWeatherUpdate() = launch(Dispatchers.IO) {
        while (isActive) {
            val weatherUpdateTasks = cityNames.map { city ->
                async { weatherUpdater(repository)(city) }
            }
            weatherUpdateTasks.awaitAll()
            delay(300000)
        }
    }


    startPeriodicWeatherUpdate()
}