package com.example.weather

import Weather
import com.example.weather.data.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Thread.sleep

typealias ProviderClient = suspend (String) -> Weather?


fun weatherUpdater(repository: WeatherRepository, weatherProviderClient: ProviderClient): suspend (String) -> Unit {
    suspend fun wrapper(cityName: String): Unit {
        var weather : Weather? = null
        var executions: Int = 0

        while (true) {
            try {
                weather = weatherProviderClient(cityName)
            } catch (e: Exception) {
                println("ERROR")
                println(e.localizedMessage)
            }
            if (weather != null) break
            executions++

            //TODO: perhaps we can use another way to try to update this city before 5 minutes
            if(executions == 7) return println("Returning without results after 7 tries")
            withContext(Dispatchers.IO) {
                sleep(500 * executions.toLong())
            }
        }
        repository.save(weather!!)
    }
    return ::wrapper
}

