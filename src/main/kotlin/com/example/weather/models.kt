import kotlinx.serialization.Serializable

enum class CityName(val city: String) {
    SANTIAGO("Santiago (CL)"),
    ZURICH("Zúrich (CH)"),
    AUCKLAND("Auckland (NZ)"),
    SIDNEY("Sídney (AU)"),
    LONDON("Londres (UK)"),
    GEORGIA("Georgia (USA)")
}

@Serializable
class Weather(
    val cityName: String, // TODO: Use CityName
    val temperature: Double,
)
