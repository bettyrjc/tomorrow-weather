package com.example.plugins

import CityName
import com.example.weather.data.RedisWeatherRepository
import com.example.weather.providers.getCityWeather
import com.example.weather.weatherUpdater
import io.ktor.server.application.*
import kotlinx.coroutines.*

fun Application.configureTimer() {
    val cityNames = arrayOf(
        CityName.SIDNEY.city,
        CityName.SANTIAGO.city,
        CityName.AUCKLAND.city,
        CityName.ZURICH.city,
        CityName.LONDON.city,
        CityName.GEORGIA.city,
    )
    val repository = RedisWeatherRepository()

    fun CoroutineScope.startPeriodicWeatherUpdate() = launch(Dispatchers.IO) {
        while (isActive) {
            val weatherUpdateTasks = cityNames.map { city ->
                async { weatherUpdater(repository, ::getCityWeather)(city) }
            }
            weatherUpdateTasks.awaitAll()
            delay(5 * 60 * 1000)
        }
    }


    startPeriodicWeatherUpdate()
}