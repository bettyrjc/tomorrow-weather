package com.example.weather

import Weather
import com.example.weather.data.WeatherRepository
import com.example.weather.providers.getCityWeather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Thread.sleep


fun weatherUpdater(repository: WeatherRepository): suspend (String) -> Unit {
    suspend fun wrapper(cityName: String): Unit {
        var weather : Weather? = null
        var executions: Int = 0

        while (true) {
            try {
                weather = getCityWeather(cityName)
            } catch (e: Exception) {
                println("ERROR")
                println(e.localizedMessage)

            }
            if (weather != null) break
            executions++

            if(executions == 7) {
                throw Error("Many failed petitions")
            }
            withContext(Dispatchers.IO) {
                sleep(2000)
            }
        }
        repository.save(weather!!)
    }
    return ::wrapper
}

