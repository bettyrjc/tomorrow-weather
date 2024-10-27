package com.example.weather.providers

import io.ktor.client.*
import io.ktor.server.application.*

import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.async

import kotlinx.serialization.json.Json

//TODO: improve in .env file a its configuration
val API_KEY = "QuOCBZGUTS8jGXtwEunWUgjNnyRLfDCA"
val BASE_URL = "https://api.tomorrow.io/v4/weather"

suspend fun getCityWeather(cityName: String): WeatherResponse? {
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
        json.decodeFromString<WeatherResponse>(responseBody)

    } catch (e: Exception) {
        println(e)
        null
    } finally {
        client.close()
    }

}

fun Application.configureClient() {
    async {
        val weather = getCityWeather("toronto")
        weather?.let { response ->
            println("Temperatura: ${response.data.values.temperature}°C")
            println("Ubicación: ${response.location.name}")
        } ?: println("No se pudo obtener el clima")
    }
}