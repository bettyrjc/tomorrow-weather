package com.example.weather.data

import Weather

interface WeatherRepository {
    fun save(weather: Weather)
    fun find(cityName: String): Weather
}