package com.example.weather.data

import Weather
import junit.framework.TestCase.assertEquals
import kotlin.test.Test

val weather = Weather(
    "Santiago", // TODO: Implement something like fakerJs for kotlin
    29.0
)

class RedisRepositoryTest {

    @Test
    fun testShouldSaveWithoutRaiseErrors() {
        val client = RedisWeatherRepository()
        client.save(weather)
    }

    @Test
    fun testShouldFindTheWeather() {
        val client = RedisWeatherRepository()
        client.save(weather)
        val foundWeather = client.find(weather.cityName)
        assertEquals(weather.cityName, foundWeather.cityName)
        assertEquals(weather.temperature, foundWeather.temperature)
    }
}