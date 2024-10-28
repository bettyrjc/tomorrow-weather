package com.example.weather

import CityName
import Weather
import com.example.weather.data.WeatherRepository
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.*
import kotlin.test.assertEquals

class WeatherUpdaterTest {
    @Test
    fun testShouldSaveTheWeather() {
        val weather = Weather(
            cityName = CityName.GEORGIA.city, //TODO: use fakers
            temperature = 12.45
        )
        val repository = mock<WeatherRepository>(WeatherRepository::class.java)
        var providerClientTries = 0
        val providerClient = { cityName: String ->
            when (cityName) {
                weather.cityName -> {
                    providerClientTries++
                    weather
                }
                else -> throw IllegalArgumentException("Unknown city")
            }
        }

        val useCase = weatherUpdater(repository, providerClient)
        runBlocking {
            useCase(weather.cityName)
        }

        verify(repository).save(weather)
        assertEquals(providerClientTries, 1)
    }

    @Test
    fun testShouldSaveTheWeatherEvenProviderFail() {
        val weather = Weather(
            cityName = CityName.GEORGIA.city, //TODO: use fakers
            temperature = 12.45
        )
        val repository = mock<WeatherRepository>(WeatherRepository::class.java)
        var providerClientTries = 0
        val providerClient = { cityName: String ->
            when (cityName) {
                weather.cityName -> {
                    providerClientTries++
                    if (providerClientTries == 1) throw Exception("Unknown city")
                    weather
                }
                else -> throw IllegalArgumentException("Unknown city")
            }
        }

        val useCase = weatherUpdater(repository, providerClient)
        runBlocking {
            useCase(weather.cityName)
        }

        verify(repository).save(weather)
        assertEquals(providerClientTries, 2)
    }
}