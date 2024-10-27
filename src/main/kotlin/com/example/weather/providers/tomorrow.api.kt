package com.example.weather.providers

import Weather
import io.ktor.client.*
import io.ktor.server.application.*

import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.async

import kotlinx.serialization.json.Json

//TODO: improve in .env file a its configuration
val API_KEY = "GP5qIJR1vE7CdMymOJoJQQeJBewEiwVN"
val BASE_URL = "https://api.tomorrow.io/v4/weather"

suspend fun getCityWeather(cityName: String): Weather? {
    val city = cityName.lowercase()
    val client = HttpClient(CIO)
    return try {
        val response: HttpResponse =
            client.get("$BASE_URL/realtime?location=${city}&apikey=$API_KEY")
        val responseBody = response.bodyAsText()
        val json = Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
        }
        val data = json.decodeFromString<WeatherResponse>(responseBody)
        val temperature = data.data.values.temperature
        val name = data.location.name
        Weather(
            cityName = name,
            temperature = temperature!!
        )

    } catch (e: Exception) {
        println("error in api")
        println(e)
        null
    } finally {
        client.close()
    }

}
