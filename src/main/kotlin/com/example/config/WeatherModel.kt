package com.example.config
import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    val data: WeatherData,
    val location: Location
)

@Serializable
data class WeatherData(
    val time: String,
    val values: WeatherValues
)

@Serializable
data class WeatherValues(
    val cloudBase: Double? = null,
    val cloudCeiling: Double? = null,
    val cloudCover: Int? = null,
    val dewPoint: Double? = null,
    val freezingRainIntensity: Int? = null,
    val humidity: Int? = null,
    val precipitationProbability: Int? = null,
    val pressureSurfaceLevel: Double? = null,
    val rainIntensity: Int? = null,
    val sleetIntensity: Int? = null,
    val snowIntensity: Int? = null,
    val temperature: Double? = null,
    val temperatureApparent: Double? = null,
    val uvHealthConcern: Int? = null,
    val uvIndex: Int? = null,
    val visibility: Int? = null,
    val weatherCode: Int? = null,
    val windDirection: Double? = null,
    val windGust: Double? = null,
    val windSpeed: Double? = null
)

@Serializable
data class Location(
    val lat: Double,
    val lon: Double,
    val name: String,
    val type: String
)