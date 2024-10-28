package com.example.plugins

import com.example.weather.data.RedisWeatherRepository

import io.github.smiley4.ktorswaggerui.SwaggerUI
import io.ktor.http.*
import io.ktor.resources.*
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.resources.Resources
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.webjars.*
import kotlinx.serialization.Serializable
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.*

@Serializable
class HttpError(val message: String?, val statusCode: Int?)

fun Application.configureRouting() {
    install(Webjars) {
        path = "/webjars" //defaults to /webjars
    }
    install(ContentNegotiation) {
        json()
    }
    install(SwaggerUI) {
        swagger {
            swaggerUrl = "swagger-ui"
            forwardRoot = true
        }
        info {
            title = "Example API"
            version = "latest"
            description = "Example API for testing and demonstration purposes."
        }
        server {
            url = "http://localhost:8080"
            description = "Development Server"
        }
    }
    install(Resources)
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
        }
    }

    val redisClient = RedisWeatherRepository()

    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        get("/webjars") {
            call.respondText("<script src='/webjars/jquery/jquery.js'></script>", ContentType.Text.Html)
        }
        get("/weather") {
            val cityName: String? = call.parameters["cityName"]
            if (cityName == null) {
                val error = HttpError("\"cityName\" is required", HttpStatusCode.PreconditionFailed.value)
                call.response.status(HttpStatusCode.PreconditionFailed)
                call.respond(error)
                return@get
            }
            try {
                val weather = redisClient.find(cityName)
                call.respond(weather)
            } catch (e: NotFoundException) {
                val error = HttpError(e.message, HttpStatusCode.NotFound.value)
                call.response.status(HttpStatusCode.NotFound)
                call.respond(error)
            }
        }
    }
}

