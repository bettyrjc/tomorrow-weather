package com.example.weather.data

import Weather
import io.ktor.server.plugins.*
import io.lettuce.core.RedisClient
import io.lettuce.core.api.async.RedisAsyncCommands
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.Json.Default.encodeToString


class RedisWeatherRepository : WeatherRepository {
    private val redisClient = RedisClient.create("redis://localhost:6379")
    private val redisCommands: RedisAsyncCommands<String, String> = redisClient.connect().async()

    override fun save(weather: Weather) {
        val weatherAsString: String = encodeToString(Weather.serializer(), weather)
       redisCommands.set(weather.cityName.lowercase(), weatherAsString).get()
    }

    override fun find(cityName: String): Weather {
        val weatherAsString = redisCommands.get(cityName.lowercase()).get() ?: throw NotFoundException("Weather not found")
        return Json.decodeFromString<Weather>(weatherAsString)
    }
}