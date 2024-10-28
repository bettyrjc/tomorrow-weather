import com.example.weather.providers.getCityWeather
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class TomorrowTest {
    @Test
    fun testShouldReturnAWeather() {
        val cityName = CityName.SANTIAGO.city
        val weather = runBlocking { getCityWeather(cityName) }
        assertIs<Weather>(weather, "Weather is null")
        assertEquals(weather.cityName, cityName)
    }
}