import kotlinx.serialization.Serializable

@Serializable
class Weather(
    val cityName: String,
    val temperature: Double,
)
