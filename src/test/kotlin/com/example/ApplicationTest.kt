package com.example

import com.example.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.*

class ApplicationTest {
    @Test
    fun testRoot() = testApplication {
        application {
            configureRouting()
        }
        client.get("/").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals("Hello World!", bodyAsText())
        }
    }

    @Test
    fun shouldReturnNotFoundCity() = testApplication {
        application { configureRouting() }
        client.get("/weather?cityName=Santiagos").apply {
            assertEquals(HttpStatusCode.NotFound, status)
            assertEquals("{\"message\":\"Weather not found\",\"statusCode\":404}", bodyAsText())
        }
    }

    @Test
    fun shouldReturnErrorIfCityNameIsNotProvided() = testApplication {
        application { configureRouting() }
        client.get("/weather").apply {
            assertEquals(HttpStatusCode.PreconditionFailed, status)
            assertEquals("{\"message\":\"\\\"cityName\\\" is required\",\"statusCode\":412}", bodyAsText())
        }
    }

    @Test
    fun shouldReturnWeather() = testApplication {
        application { configureRouting() }
        client.get("/weather?cityName=Santiago").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals("{\"cityName\":\"Santiago\",\"temperature\":12.1}", bodyAsText())
        }
    }
}
