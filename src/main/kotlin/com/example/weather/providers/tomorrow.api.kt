package com.example.weather.providers

import CityName
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

val Cities = hashMapOf(
    CityName.SANTIAGO.city to "santiago chile",
    CityName.ZURICH.city to "zurich Schweiz",
    CityName.AUCKLAND.city to "auckland new zealand",
    CityName.SIDNEY.city to "sidney australia",
    CityName.LONDON.city to "london UK",
    CityName.GEORGIA.city to "georgia USA",

)

suspend fun getCityWeather(cityName: String): Weather? {
    val city = Cities[cityName] ?: throw Exception("Invalid city name.") //TODO: use custom error
    val client = HttpClient(CIO)
    return try {
        val response: HttpResponse =
            client.get("$BASE_URL/realtime") {
                url {
                    parameters.append("location", city)
                    parameters.append("apikey", API_KEY)
                }
            }
        val responseBody = response.bodyAsText()
        val json = Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
        }
        val data = json.decodeFromString<WeatherResponse>(responseBody)
        val temperature = data.data.values.temperature
        Weather(
            cityName = cityName,
            temperature = temperature
        )

    } catch (e: Exception) {
        println("error in api")
        println(e)
        null
    } finally {
        client.close()
    }

}
