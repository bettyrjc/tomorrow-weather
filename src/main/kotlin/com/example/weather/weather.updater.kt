package com.example.weather

import Weather
import com.example.weather.data.WeatherRepository
import com.example.weather.providers.getCityWeather
import io.ktor.server.application.*
import kotlin.reflect.jvm.internal.impl.builtins.functions.FunctionTypeKind.KSuspendFunction


fun weatherUpdater(repository: WeatherRepository): suspend (String) -> Unit {
    suspend fun wrapper(cityName: String): Unit {
        var weather : Weather? = null
        while (weather == null) {
            try {
                weather = getCityWeather(cityName)
            } catch (e: Exception) {
                println(e.localizedMessage)
            }
        }
        repository.save(weather)
    }
    return ::wrapper
}

