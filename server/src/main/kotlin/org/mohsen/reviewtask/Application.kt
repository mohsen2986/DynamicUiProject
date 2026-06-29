package org.mohsen.reviewtask

import io.ktor.http.ContentType
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

private val textUserInputResponse = """
        {
          "screenId": "home",
          "title": "User Input",
          "component": {
            "type": "text",
            "id": "user_prompt",
            "label": "Ask something!",
            "placeholder": "Type..."
          }
        }
    """.trimIndent()

private val numberUserInputResponse = """
       {
         "screenId": "home",
         "title": "Age Input",
         "component": {
           "type": "number",
           "id": "age",
           "label": "Age",
           "placeholder": "Enter age"
         }
       }
    """.trimIndent()

private val sliderInputResponse = """
        {
          "screenId": "home",
          "title": "Volume",
          "component": {
            "type": "slider",
            "id": "volume",
            "label": "Volume",
            "min": 0,
            "max": 100,
            "value": 50
          }
        }
    """.trimIndent()

private val emptyBoxResponse = """
        {
          "screenId": "home",
          "title": "Colored Box",
          "component": {
            "type": "empty_box",
            "color": "#FF0000"
          }
        }
    """.trimIndent()

private val failedResponse = """
        {
          "screenId": "home",
          "title": "Volume",
          "component": {
          
          }
        }
    """.trimIndent()


fun Application.module() {
    routing {
        get("/screen/home") {
            call.respondText(
                failedResponse,
                ContentType.Application.Json
            )
        }
    }
}